package com.sbilsky.data.storage.api.sql.contract;

import android.content.ContentValues;

import com.sbilsky.data.storage.models.LanguageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class LanguagesContract {
    public static final String TABLE_NAME = LanguagesContract.class.getSimpleName();

    public enum Contract {
        ABBREVIATION,
        NAME
    }

    public static String createTable() {
        return "create table " + TABLE_NAME + " ("
                + Contract.ABBREVIATION.name() + " text,"
                + Contract.NAME.name() + " text"
                + ");";
    }

    public static String getAllLanguages() {
        return "select * from " + TABLE_NAME;
    }

    public static String getLanguageNameByAbbreviation(String abbreviation) {
        return "select " + Contract.NAME.name() + " from " + TABLE_NAME +
                " WHERE " + Contract.ABBREVIATION.name() + " = " + "'" + abbreviation + "'";
    }


    public static List<ContentValues> setAllLanguages(List<LanguageModel> languageModels) {
        List<ContentValues> contentValues = new ArrayList<>();

        for (int i = 0; i < languageModels.size(); i++) {
            ContentValues contentValue = new ContentValues();
            contentValue.put(Contract.ABBREVIATION.name(), languageModels.get(i).getAbbreviation());
            contentValue.put(Contract.NAME.name(), languageModels.get(i).getName());
            contentValues.add(contentValue);
        }
        return contentValues;
    }

}
