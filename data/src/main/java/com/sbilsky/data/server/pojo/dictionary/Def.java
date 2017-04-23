package com.sbilsky.data.server.pojo.dictionary;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class Def implements Parcelable {
    private String text;
    private String pos;
    private List<Tr> tr = null;

    private Def(Parcel in) {
        text = in.readString();
        pos = in.readString();
    }

    public static final Parcelable.Creator<Def> CREATOR = new Parcelable.Creator<Def>() {
        @Override
        public Def createFromParcel(Parcel in) {
            return new Def(in);
        }

        @Override
        public Def[] newArray(int size) {
            return new Def[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public List<Tr> getTr() {
        return tr;
    }

    public void setTr(List<Tr> tr) {
        this.tr = tr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(pos);
    }
}
