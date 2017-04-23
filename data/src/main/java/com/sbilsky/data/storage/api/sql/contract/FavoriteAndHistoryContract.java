package com.sbilsky.data.storage.api.sql.contract;

import android.content.ContentValues;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class FavoriteAndHistoryContract {
    public static final String TABLE_NAME = FavoriteAndHistoryContract.class.getSimpleName();

    public enum Contract {
        ID,
        WORD_FROM_TRANSLATE,
        WORD_TO_TRANSLATE,
        DICTIONARY_JSON,
        LANGUAGE_FROM_TRANSLATE,
        LANGUAGE_TO_TRANSLATE,
        IS_FAVORITE,
        IS_HISTORY,
        CHANGED
    }

    public static String createTable() {
        return "create table " + TABLE_NAME + " ("
                + Contract.ID.name() + " integer  primary key,"
                + Contract.WORD_FROM_TRANSLATE.name() + " text,"
                + Contract.WORD_TO_TRANSLATE.name() + " text,"
                + Contract.DICTIONARY_JSON.name() + " text,"
                + Contract.LANGUAGE_FROM_TRANSLATE.name() + " text,"
                + Contract.LANGUAGE_TO_TRANSLATE.name() + " text,"
                + Contract.IS_HISTORY.name() + " integer,"
                + Contract.IS_FAVORITE.name() + " integer DEFAULT 0,"
                + Contract.CHANGED.name() + " long"
                + "," + " unique (" + Contract.WORD_FROM_TRANSLATE.name() + "," + Contract.WORD_TO_TRANSLATE.name() +
                "," + Contract.LANGUAGE_TO_TRANSLATE.name() + "," + Contract.LANGUAGE_FROM_TRANSLATE.name() + ") "
                + ") " + ";";
    }

    public static final String LANGUAGE_NAME_FROM = "LANGUAGE_NAME_FROM";
    public static final String LANGUAGE_NAME_TO = "LANGUAGE_NAME_TO";

    public static String getAllFavorite() {
        return "select " + TABLE_NAME + ".*, TR1." + LanguagesContract.Contract.NAME.name() + " as " + LANGUAGE_NAME_FROM + ", " +
                "TR2." + LanguagesContract.Contract.NAME.name() + " as " + LANGUAGE_NAME_TO + " from " + TABLE_NAME +
                " LEFT JOIN " + LanguagesContract.TABLE_NAME + " as TR1 ON "
                + TABLE_NAME + "." + Contract.LANGUAGE_FROM_TRANSLATE.name() + " = TR1." + LanguagesContract.Contract.ABBREVIATION.name() +
                " LEFT JOIN " + LanguagesContract.TABLE_NAME + " as TR2 ON "
                + TABLE_NAME + "." + Contract.LANGUAGE_TO_TRANSLATE.name() + " = TR2." + LanguagesContract.Contract.ABBREVIATION.name() +
                " WHERE " + Contract.IS_FAVORITE.name() + " = " + "1"
                + " ORDER BY " + Contract.CHANGED.name() + " DESC";
    }

    public static String getAllHistory() {
        return "select " + TABLE_NAME + ".*, TR1." + LanguagesContract.Contract.NAME.name() + " as " + LANGUAGE_NAME_FROM + ", " +
                "TR2." + LanguagesContract.Contract.NAME.name() + " as " + LANGUAGE_NAME_TO + " from " + TABLE_NAME +
                " LEFT JOIN " + LanguagesContract.TABLE_NAME + " as TR1 ON "
                + TABLE_NAME + "." + Contract.LANGUAGE_FROM_TRANSLATE.name() + " = TR1." + LanguagesContract.Contract.ABBREVIATION.name() +
                " LEFT JOIN " + LanguagesContract.TABLE_NAME + " as TR2 ON "
                + TABLE_NAME + "." + Contract.LANGUAGE_TO_TRANSLATE.name() + " = TR2." + LanguagesContract.Contract.ABBREVIATION.name() +
                " WHERE " + Contract.IS_HISTORY.name() + " = " + "1"
                + " ORDER BY " + Contract.CHANGED.name() + " DESC";
    }

    public static String getEntryById(int id){
        return "select " + TABLE_NAME + ".*, TR1." + LanguagesContract.Contract.NAME.name() + " as " + LANGUAGE_NAME_FROM + ", " +
                "TR2." + LanguagesContract.Contract.NAME.name() + " as " + LANGUAGE_NAME_TO + " from " + TABLE_NAME +
                " LEFT JOIN " + LanguagesContract.TABLE_NAME + " as TR1 ON "
                + TABLE_NAME + "." + Contract.LANGUAGE_FROM_TRANSLATE.name() + " = TR1." + LanguagesContract.Contract.ABBREVIATION.name() +
                " LEFT JOIN " + LanguagesContract.TABLE_NAME + " as TR2 ON "
                + TABLE_NAME + "." + Contract.LANGUAGE_TO_TRANSLATE.name() + " = TR2." + LanguagesContract.Contract.ABBREVIATION.name() +
                " WHERE " + Contract.ID.name() + " = " + id
                + " ORDER BY " + Contract.CHANGED.name() + " DESC";
    }
    public static String getFavoriteStatusById(int id) {
        return "select " + Contract.IS_FAVORITE.name() + " from " + TABLE_NAME
                + " WHERE " + Contract.ID.name() + " = " + id;

    }

    public static String deleteAllFavorite() {
        return "update  " + TABLE_NAME
                + " SET " + Contract.IS_FAVORITE.name() + " = " + "0"
                + " WHERE " + Contract.IS_FAVORITE.name() + " = " + "1";
    }

    public static String deleteAllHistory() {
        return "update " + TABLE_NAME
                + " SET " + Contract.IS_HISTORY.name() + " = " + "0"
                + " WHERE " + Contract.IS_HISTORY.name() + " = " + "1";
    }

    public static String deleteItemFromFavorite(int id) {
        return "update " + TABLE_NAME
                + " SET " + Contract.IS_FAVORITE.name() + " = " + "0"
                + " WHERE (" + Contract.ID.name() + " = " + id + ")";
    }

    public static String addItemFromFavorite(int id) {
        return "update " + TABLE_NAME
                + " SET " + Contract.IS_FAVORITE.name() + " = " + "1"
                + " WHERE (" + Contract.ID.name() + " = " + id + ")";
    }

    public static String deleteItemFromHistory(int id) {
        return "update " + TABLE_NAME
                + " SET " + Contract.IS_HISTORY.name() + " = " + "0"
                + " WHERE (" + Contract.ID.name() + " = " + id + ")";
    }

    public static String clearDataNoFavoriteAndNoHistory() {
        return "delete from " + TABLE_NAME
                + " WHERE (" + Contract.IS_FAVORITE.name() + " = " + "0"
                + " AND " + Contract.IS_HISTORY.name() + " = " + "0" + ")";
    }

    public static ContentValues addToTable(String wordFromTranslate,
                                           String wordToTranslate,
                                           String dictionaryJson,
                                           String languageFromTranslate,
                                           String languageToTranslate, long changed) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.WORD_FROM_TRANSLATE.name(), wordFromTranslate);
        contentValues.put(Contract.WORD_TO_TRANSLATE.name(), wordToTranslate);
        contentValues.put(Contract.DICTIONARY_JSON.name(), dictionaryJson);
        contentValues.put(Contract.LANGUAGE_FROM_TRANSLATE.name(), languageFromTranslate);
        contentValues.put(Contract.LANGUAGE_TO_TRANSLATE.name(), languageToTranslate);
        contentValues.put(Contract.IS_HISTORY.name(), 1);
        contentValues.put(Contract.CHANGED.name(), changed);
        return contentValues;
    }
    public static String getRowId(){
        return "Select " +Contract.ID.name()+ " from "+ TABLE_NAME +" WHERE " +"("+
                Contract.WORD_FROM_TRANSLATE.name()+" = ?"+" AND "+
                Contract.WORD_TO_TRANSLATE.name()+" = ?"+" AND "+
                Contract.LANGUAGE_FROM_TRANSLATE.name()+" = ?"+" AND "+
                Contract.LANGUAGE_TO_TRANSLATE.name()+" = ?"+" )";

    }
}
