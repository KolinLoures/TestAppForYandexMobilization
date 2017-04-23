package com.sbilsky.data.storage.mappers;

import android.database.Cursor;

import com.sbilsky.data.storage.api.sql.contract.LanguagesContract;
import com.sbilsky.data.storage.models.LanguageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class LanguageMapper {
    public static List<LanguageModel> mapCursorToLanguageModels(Cursor cursor) {
        List<LanguageModel> languageModels = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            int abbColumn = cursor.getColumnIndex(LanguagesContract.Contract.ABBREVIATION.name());
            int langNameColumn = cursor.getColumnIndex(LanguagesContract.Contract.NAME.name());
            while (!cursor.isAfterLast()) {
                String langAbb = cursor.getString(abbColumn);
                String langName = cursor.getString(langNameColumn);
                languageModels.add(new LanguageModel(langName, langAbb));
                cursor.moveToNext();
            }
        }
        return languageModels;
    }
}
