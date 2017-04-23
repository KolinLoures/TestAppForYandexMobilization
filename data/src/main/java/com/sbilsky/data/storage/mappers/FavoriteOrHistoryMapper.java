package com.sbilsky.data.storage.mappers;

import android.database.Cursor;

import com.google.gson.Gson;
import com.sbilsky.data.server.pojo.dictionary.Dictionary;
import com.sbilsky.data.storage.api.sql.contract.FavoriteAndHistoryContract;
import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;
import com.sbilsky.data.storage.models.LanguageModel;
import com.sbilsky.data.storage.models.TranslateModel;
import com.sbilsky.data.storage.models.TranslationModel;
import com.sbilsky.data.storage.models.VariantTranslateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class FavoriteOrHistoryMapper {


    public static List<FavoriteOrHistoryModel> mapCursorToListFavoriteOrHistoryModel(Cursor cursor) {
        List<FavoriteOrHistoryModel> favoriteOrHistoryModels = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int idColumn = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.ID.name());
            int columnWordFrom = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.WORD_FROM_TRANSLATE.name());
            int columnWordTo = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.WORD_TO_TRANSLATE.name());
            int columnLangFrom = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.LANGUAGE_FROM_TRANSLATE.name());
            int columnLangTo = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.LANGUAGE_TO_TRANSLATE.name());
            int columnIsFavorite = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.IS_FAVORITE.name());
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(idColumn);
                String wordFrom = cursor.getString(columnWordFrom);
                String wordTo = cursor.getString(columnWordTo);
                String langFrom = cursor.getString(columnLangFrom);
                String langTo = cursor.getString(columnLangTo);
                boolean favorite = cursor.getInt(columnIsFavorite) == 1;
                favoriteOrHistoryModels.add(new FavoriteOrHistoryModel(id, wordFrom, wordTo, langFrom, langTo, favorite));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return favoriteOrHistoryModels;
    }

    public static Boolean mapCursorToFavoriteStatus(Cursor cursor) {
        Boolean status = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int columnIsFavorite = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.IS_FAVORITE.name());
            status = cursor.getInt(columnIsFavorite) == 1;
        }
        return status;
    }

    public static TranslationModel mapCursorToTranslationModel(Cursor cursor, int position) {
        if (cursor != null) {
            if (cursor.moveToPosition(position)) {
                cursor.moveToPosition(position);
                int idColumn = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.ID.name());
                int columnWordFrom = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.WORD_FROM_TRANSLATE.name());
                int columnWordTo = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.WORD_TO_TRANSLATE.name());
                int dictionaryJson = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.DICTIONARY_JSON.name());
                int columnIsFavorite = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.IS_FAVORITE.name());
                int id = cursor.getInt(idColumn);
                int columnLangAbbreviationFrom = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.LANGUAGE_FROM_TRANSLATE.name());
                int columnLangNameFrom = cursor.getColumnIndex(FavoriteAndHistoryContract.LANGUAGE_NAME_FROM);
                int columnLangAbbreviationTo = cursor.getColumnIndex(FavoriteAndHistoryContract.Contract.LANGUAGE_TO_TRANSLATE.name());
                int columnLangNameTo = cursor.getColumnIndex(FavoriteAndHistoryContract.LANGUAGE_NAME_TO);
                String word = cursor.getString(columnWordFrom);
                String translate = cursor.getString(columnWordTo);
                String dictionaryString = cursor.getString(dictionaryJson);
                String langAbbreviationFrom = cursor.getString(columnLangAbbreviationFrom);
                String langAbbreviationTo = cursor.getString(columnLangAbbreviationTo);
                String langNameFrom = cursor.getString(columnLangNameFrom);
                String langNameTo = cursor.getString(columnLangNameTo);
                LanguageModel languageModelFrom = new LanguageModel(langNameFrom, langAbbreviationFrom);
                LanguageModel languageModelTo = new LanguageModel(langNameTo, langAbbreviationTo);
                boolean favorite = cursor.getInt(columnIsFavorite) == 1;
                int cursorSize = cursor.getCount();
                cursor.close();
                Dictionary dictionary = null;
                if (dictionaryString != null) {
                    Gson gson = new Gson();
                    dictionary = gson.fromJson(dictionaryString, Dictionary.class);
                }
                return mapTranslationAndDictionary(id, word, translate, dictionary, favorite, cursorSize, languageModelFrom, languageModelTo);
            } else {
                return null;
            }

        }
        return null;

    }

    private static TranslationModel mapTranslationAndDictionary(int id, String text, String translation, Dictionary dictionary, boolean favorite, int cursorSize, LanguageModel languageModelFrom, LanguageModel languageModelTo) {
        if (dictionary == null) {
            return new TranslationModel(id, text, translation, favorite, cursorSize, languageModelFrom, languageModelTo);
        } else {
            List<TranslateModel> translateModels = new ArrayList<>();
            for (int i = 0; i < dictionary.getDef().size(); i++) {
                String partOfSpeech = dictionary.getDef().get(i).getPos();
                String word = dictionary.getDef().get(i).getText();
                List<VariantTranslateModel> variantTranslateModels = new ArrayList<>();
                for (int j = 0; j < dictionary.getDef().get(i).getTr().size(); j++) {
                    String translate = dictionary.getDef().get(i).getTr().get(j).getText();
                    if (dictionary.getDef().get(i).getTr().get(j).getSyn() != null && dictionary.getDef().get(i).getTr().get(j).getSyn().size() > 0) {
                        for (int z = 0; z < dictionary.getDef().get(i).getTr().get(j).getSyn().size(); z++) {
                            translate = translate + ", " + dictionary.getDef().get(i).getTr().get(j).getSyn().get(z).getText();

                        }
                    }
                    String fromVariant = null;
                    if (dictionary.getDef().get(i).getTr().get(j).getMean() != null && dictionary.getDef().get(i).getTr().get(j).getMean().size() > 0) {
                        fromVariant = "(";
                        for (int z = 0; z < dictionary.getDef().get(i).getTr().get(j).getMean().size(); z++) {
                            if (z == 0) {
                                fromVariant = fromVariant + dictionary.getDef().get(i).getTr().get(j).getMean().get(z).getText();
                                if (dictionary.getDef().get(i).getTr().get(j).getMean().size() == 1) {
                                    fromVariant = fromVariant + ")";
                                }
                            } else if (z != dictionary.getDef().get(i).getTr().get(j).getMean().size() - 1) {
                                fromVariant = fromVariant + ", " + dictionary.getDef().get(i).getTr().get(j).getMean().get(z).getText();
                            } else {
                                fromVariant = fromVariant + ", " + dictionary.getDef().get(i).getTr().get(j).getMean().get(z).getText() + ")";
                            }
                        }
                    }
                    String samples = null;
                    if (dictionary.getDef().get(i).getTr().get(j).getEx() != null && dictionary.getDef().get(i).getTr().get(j).getEx().size() > 0) {
                        samples = "";
                        for (int z = 0; z < dictionary.getDef().get(i).getTr().get(j).getEx().size(); z++) {

                            if (z != dictionary.getDef().get(i).getTr().get(j).getEx().size() - 1) {
                                samples = samples + dictionary.getDef().get(i).getTr().get(j).getEx().get(z).getText() + " - " + dictionary.getDef().get(i).getTr().get(j).getEx().get(z).getTr().get(0).getText() + "\n";
                            } else {
                                samples = samples + dictionary.getDef().get(i).getTr().get(j).getEx().get(z).getText() + " - " + dictionary.getDef().get(i).getTr().get(j).getEx().get(z).getTr().get(0).getText();
                            }
                        }
                    }
                    VariantTranslateModel variantTranslateModel = new VariantTranslateModel(translate, fromVariant, samples);
                    variantTranslateModels.add(variantTranslateModel);
                }
                TranslateModel translateModel = new TranslateModel(word, partOfSpeech, variantTranslateModels);
                translateModels.add(translateModel);

            }
            return new TranslationModel(id, text, translation, translateModels, favorite, cursorSize, languageModelFrom, languageModelTo);
        }
    }
}
