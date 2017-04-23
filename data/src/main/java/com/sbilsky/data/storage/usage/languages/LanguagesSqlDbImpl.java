package com.sbilsky.data.storage.usage.languages;

import android.database.Cursor;

import com.sbilsky.data.storage.api.sql.SQLDataBaseHelper;
import com.sbilsky.data.storage.api.sql.contract.LanguagesContract;
import com.sbilsky.data.storage.models.LanguageModel;

import java.util.List;

import static com.sbilsky.data.storage.mappers.LanguageMapper.mapCursorToLanguageModels;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class LanguagesSqlDbImpl implements ILanguagesStorage {
    @Override
    public List<LanguageModel> getAllLanguages() {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        Cursor cursor = sqlDataBaseHelper.getCursor(LanguagesContract.getAllLanguages());
        return mapCursorToLanguageModels(cursor);
    }

    @Override
    public String getLanguageNameByAbbreviation(String abbreviation) {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        Cursor cursor = sqlDataBaseHelper.getCursor(LanguagesContract.getLanguageNameByAbbreviation(abbreviation));
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    @Override
    public void setAllLanguages(List<LanguageModel> languageModels) {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        sqlDataBaseHelper.writeToTable(LanguagesContract.setAllLanguages(languageModels), LanguagesContract.TABLE_NAME);
    }
}
