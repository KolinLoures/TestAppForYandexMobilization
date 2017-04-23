package com.sbilsky.data.storage.api.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.sbilsky.data.application.YandexTestAppApplication;

/**
 * @author Cвятослав Бильский s.bislky
 */

class SharedPreferencesSaver {
    private final static String PREF_FILENAME = "YandexTestApplicationTranslatorSharedPreferences";
    private static SharedPreferences sharedPref = null;

    private static SharedPreferences getInstance() {
        if (sharedPref == null) {
            sharedPref = YandexTestAppApplication.getAppContext().getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE);
        }
        return sharedPref;
    }

    static synchronized void saveToSharedPref(final String key, final PreferencesType type, final Object value) {
        final SharedPreferences.Editor editor = getInstance().edit();

        switch (type) {
            case STRING:
                editor.putString(key, (String) value);
                break;
            case INTEGER:
                editor.putInt(key, (Integer) value);
                break;
            case BOOLEAN:
                editor.putBoolean(key, (Boolean) value);
                break;
            case LONG:
                editor.putLong(key, (Long) value);
                break;
            default:
                break;
        }
        editor.apply();
    }

    static Object getFromSharedPref(final String key, final PreferencesType type, final Object defValue) {
        int DEFAULT = -1;
        final SharedPreferences editor = getInstance();
        Object a = null;
        switch (type) {
            case STRING:
                a = editor.getString(key, (String) defValue);
                break;
            case INTEGER:
                a = editor.getInt(key, defValue != null ? ((Integer) defValue) : DEFAULT);
                break;
            case LONG:
                a = editor.getLong(key, defValue != null ? ((Long) defValue) : DEFAULT);
                break;
            case BOOLEAN:
                a = editor.getBoolean(key, defValue != null ? ((Boolean) defValue) : false);   // если необходимо возвращать null то придется через TRY-CATCH обходить ограничение метода с типом boolean
                break;
        }
        return a;
    }
}
