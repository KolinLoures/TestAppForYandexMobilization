package com.sbilsky.data.storage.api.preferences;

/**
 * @author Cвятослав Бильский s.bislky
 */

public enum Preferences {
    LANGUAGE_FROM(PreferencesType.STRING, "en"),
    LANGUAGE_TO(PreferencesType.STRING, "ru"),
    DICTIONARY_ENABLED(PreferencesType.BOOLEAN, true);
    private final PreferencesType mType;
    private final Object mDefValue;

    Preferences(PreferencesType type, Object defValue) {
        this.mType = type;
        this.mDefValue = defValue;
    }

    public PreferencesType getType() {
        return mType;
    }

    public void set(final Object value) {
        SharedPreferencesSaver.saveToSharedPref(name(), getType(), value);
    }

    public Object get(final Object defValue) {
        return SharedPreferencesSaver.getFromSharedPref(name(), getType(), defValue);
    }

    public Object get() {
        return get(mDefValue);
    }

    public String getString() {
        return (String) get(mDefValue);
    }

    public Integer getInteger() {
        return (Integer) get(mDefValue);
    }

    public Boolean getBoolean() {
        return (Boolean) get(mDefValue);
    }

    public Boolean getInvertBoolean() {
        return (Boolean) get(true);
    }

    public Long getLong() {
        return (Long) get(mDefValue);
    }
}
