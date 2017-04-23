package com.sbilsky.data.server.pojo;

import android.os.Parcel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class Translation {
    private int code;
    private String lang;
    private List<String> text = null;

    protected Translation(Parcel in) {
        code = in.readInt();
        lang = in.readString();
        text = in.createStringArrayList();
    }


    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
}
