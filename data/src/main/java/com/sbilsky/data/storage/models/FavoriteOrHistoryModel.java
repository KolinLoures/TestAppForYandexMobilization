package com.sbilsky.data.storage.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Cвятослав Бильский s.bislky
 *
 */

public class FavoriteOrHistoryModel implements Parcelable {
    private  int id;
    private String textFrom,textTo,langAbbreviationFrom,langAbbreviationTo;
    private boolean isFavorite;

    public FavoriteOrHistoryModel(int id, String textFrom, String textTo, String langAbbreviationFrom, String langAbbreviationTo, boolean isFavorite) {
        this.id = id;
        this.textFrom = textFrom;
        this.textTo = textTo;
        this.langAbbreviationFrom = langAbbreviationFrom;
        this.langAbbreviationTo = langAbbreviationTo;
        this.isFavorite = isFavorite;
    }


    protected FavoriteOrHistoryModel(Parcel in) {
        id = in.readInt();
        textFrom = in.readString();
        textTo = in.readString();
        langAbbreviationFrom = in.readString();
        langAbbreviationTo = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<FavoriteOrHistoryModel> CREATOR = new Creator<FavoriteOrHistoryModel>() {
        @Override
        public FavoriteOrHistoryModel createFromParcel(Parcel in) {
            return new FavoriteOrHistoryModel(in);
        }

        @Override
        public FavoriteOrHistoryModel[] newArray(int size) {
            return new FavoriteOrHistoryModel[size];
        }
    };

    public String getTextFrom() {
        return textFrom;
    }

    public void setTextFrom(String textFrom) {
        this.textFrom = textFrom;
    }

    public String getTextTo() {
        return textTo;
    }

    public void setTextTo(String textTo) {
        this.textTo = textTo;
    }

    public String getLangAbbreviationFrom() {
        return langAbbreviationFrom;
    }

    public void setLangAbbreviationFrom(String langAbbreviationFrom) {
        this.langAbbreviationFrom = langAbbreviationFrom;
    }

    public String getLangAbbreviationTo() {
        return langAbbreviationTo;
    }

    public void setLangAbbreviationTo(String langAbbreviationTo) {
        this.langAbbreviationTo = langAbbreviationTo;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(textFrom);
        parcel.writeString(textTo);
        parcel.writeString(langAbbreviationFrom);
        parcel.writeString(langAbbreviationTo);
        parcel.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
