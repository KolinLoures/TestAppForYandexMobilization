package com.sbilsky.data.storage.api.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sbilsky.data.storage.api.sql.contract.FavoriteAndHistoryContract;
import com.sbilsky.data.storage.api.sql.contract.LanguagesContract;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class SQLDataBaseHelper extends SQLiteOpenHelper {
    private static SQLDataBaseHelper dbInstance;

    private static String dbName = "yandex.translator.testapp.db";
    private static int dbVersion = 1;

    private SQLDataBaseHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }


    public static void initDBHelper(Context context) {
        if (dbInstance == null) {
            dbInstance = new SQLDataBaseHelper(context);
        }
    }

    public static synchronized SQLDataBaseHelper getInstance() {
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(FavoriteAndHistoryContract.createTable());
        sqLiteDatabase.execSQL(LanguagesContract.createTable());
        writeToTable(sqLiteDatabase, LanguagesContract.setAllLanguages(LanguageFirstIniter.getLanguageForFirstInit()), LanguagesContract.TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getCursor(String query) {
        SQLiteDatabase db = dbInstance.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public Cursor getCursor(String query,String [] selectionArgs) {
        SQLiteDatabase db = dbInstance.getReadableDatabase();
        return db.rawQuery(query, selectionArgs);
    }
    public void writeToTable(ContentValues values, String TABLE_NAME,int rowID) {
        SQLiteDatabase db = dbInstance.getWritableDatabase();
         if(rowID==-1)
        db.insert(TABLE_NAME, null, values);
        else
            db.update(TABLE_NAME, values, "ID=?", new String[] {Integer.toString(rowID)});

    }


    public void writeToTable(List<ContentValues> contentValues, String TABLE_NAME) {
        SQLiteDatabase db = dbInstance.getWritableDatabase();
        db.beginTransaction();
        for (int i = 0; i < contentValues.size(); i++) {
            db.insert(TABLE_NAME, null, contentValues.get(i));
        }
        db.endTransaction();
    }

    private void writeToTable(SQLiteDatabase db, List<ContentValues> contentValues, String TABLE_NAME) {
        for (int i = 0; i < contentValues.size(); i++) {
            db.insert(TABLE_NAME, null, contentValues.get(i));
        }
    }

    public void execSQL(String query) {
        SQLiteDatabase db = dbInstance.getWritableDatabase();
        db.execSQL(query);

    }


}
