package com.sbilsky.data.storage.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class VariantTranslateModel implements Parcelable {
    private String translate, fromVariant, samples;

    public VariantTranslateModel(String translate, String fromVariant, String samples) {
        this.translate = translate;
        this.fromVariant = fromVariant;
        this.samples = samples;
    }

    private VariantTranslateModel(Parcel in) {
        translate = in.readString();
        fromVariant = in.readString();
        samples = in.readString();
    }

    public static final Creator<VariantTranslateModel> CREATOR = new Creator<VariantTranslateModel>() {
        @Override
        public VariantTranslateModel createFromParcel(Parcel in) {
            return new VariantTranslateModel(in);
        }

        @Override
        public VariantTranslateModel[] newArray(int size) {
            return new VariantTranslateModel[size];
        }
    };

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getFromVariant() {
        return fromVariant;
    }

    public void setFromVariant(String fromVariant) {
        this.fromVariant = fromVariant;
    }

    public String getSamples() {
        return samples;
    }

    public void setSamples(String samples) {
        this.samples = samples;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(translate);
        parcel.writeString(fromVariant);
        parcel.writeString(samples);
    }
}
