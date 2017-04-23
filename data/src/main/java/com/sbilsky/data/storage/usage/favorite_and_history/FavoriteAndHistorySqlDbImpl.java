package com.sbilsky.data.storage.usage.favorite_and_history;

import android.database.Cursor;

import com.sbilsky.data.storage.api.sql.SQLDataBaseHelper;
import com.sbilsky.data.storage.api.sql.contract.FavoriteAndHistoryContract;
import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;
import com.sbilsky.data.storage.models.TranslationModel;

import java.util.Calendar;
import java.util.List;

import static com.sbilsky.data.storage.mappers.FavoriteOrHistoryMapper.mapCursorToFavoriteStatus;
import static com.sbilsky.data.storage.mappers.FavoriteOrHistoryMapper.mapCursorToListFavoriteOrHistoryModel;
import static com.sbilsky.data.storage.mappers.FavoriteOrHistoryMapper.mapCursorToTranslationModel;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class FavoriteAndHistorySqlDbImpl implements IFavoriteAndHistoryStorage {
    @Override
    public List<FavoriteOrHistoryModel> getAllFavorite() {
        Cursor cursor = getAllFavoriteCursor();
        return mapCursorToListFavoriteOrHistoryModel(cursor);
    }

    @Override
    public List<FavoriteOrHistoryModel> getAllHistory() {
        Cursor cursor = getAllHistoryCursor();
        return mapCursorToListFavoriteOrHistoryModel(cursor);
    }

    @Override
    public TranslationModel getHistoryFromPosition(int position) {
        Cursor cursor = getAllHistoryCursor();
        return mapCursorToTranslationModel(cursor, position);
    }

    @Override
    public TranslationModel getFavoriteFromPosition(int position) {
        Cursor cursor = getAllFavoriteCursor();
        return mapCursorToTranslationModel(cursor, position);
    }

    public TranslationModel getEntryById(int id){
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        Cursor cursor= sqlDataBaseHelper.getCursor(FavoriteAndHistoryContract.getEntryById(id));
        return mapCursorToTranslationModel(cursor, 0);
    }

    @Override
    public Boolean getFavoriteStatusById(int id) {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        Cursor cursor = sqlDataBaseHelper.getCursor(FavoriteAndHistoryContract.getFavoriteStatusById(id));
        return mapCursorToFavoriteStatus(cursor);
    }

    private Cursor getAllFavoriteCursor() {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        return sqlDataBaseHelper.getCursor(FavoriteAndHistoryContract.getAllFavorite());
    }

    private Cursor getAllHistoryCursor() {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        return sqlDataBaseHelper.getCursor(FavoriteAndHistoryContract.getAllHistory());
    }

    @Override
    public void addToFavorite(int id) {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        sqlDataBaseHelper.execSQL(FavoriteAndHistoryContract.addItemFromFavorite(id));
    }

    @Override
    public void deleteAllFavorite() {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        sqlDataBaseHelper.execSQL(FavoriteAndHistoryContract.deleteAllFavorite());
    }

    @Override
    public void deleteAllHistory() {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        sqlDataBaseHelper.execSQL(FavoriteAndHistoryContract.deleteAllHistory());
    }

    @Override
    public void deleteItemFromFavorite(int id) {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        sqlDataBaseHelper.execSQL(FavoriteAndHistoryContract.deleteItemFromFavorite(id));
    }

    @Override
    public void deleteItemFromHistory(int id) {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        sqlDataBaseHelper.execSQL(FavoriteAndHistoryContract.deleteItemFromHistory(id));
    }

    @Override
    public void clearDataNoFavoriteAndNoHistory() {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        sqlDataBaseHelper.execSQL(FavoriteAndHistoryContract.clearDataNoFavoriteAndNoHistory());
    }

    @Override
    public void addToTable(String wordFromTranslate, String wordToTranslate, String dictionaryJson, String languageFromTranslate, String languageToTranslate) {
        SQLDataBaseHelper sqlDataBaseHelper = SQLDataBaseHelper.getInstance();
        String selectionArgs []={wordFromTranslate,wordToTranslate,languageFromTranslate,languageToTranslate};

        Cursor cursorRow=sqlDataBaseHelper.getCursor(FavoriteAndHistoryContract.getRowId(),selectionArgs);
        int id=-1;
        if (cursorRow!=null&&cursorRow.moveToFirst()) {
            cursorRow.moveToFirst();
            id = cursorRow.getInt(cursorRow.getColumnIndex(FavoriteAndHistoryContract.Contract.ID.name()));
            cursorRow.close();
        }
        sqlDataBaseHelper.writeToTable(FavoriteAndHistoryContract.addToTable(wordFromTranslate, wordToTranslate, dictionaryJson, languageFromTranslate, languageToTranslate, Calendar.getInstance().getTimeInMillis()),FavoriteAndHistoryContract.TABLE_NAME,id);
    }


}
