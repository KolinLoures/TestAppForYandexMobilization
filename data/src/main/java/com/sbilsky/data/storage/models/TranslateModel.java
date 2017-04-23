package com.sbilsky.data.storage.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class TranslateModel implements Parcelable {
    private String wordFromTranslate, partOfSpeech;
    private List<VariantTranslateModel> variantTranslateModels;

    public TranslateModel(String wordFromTranslate, String partOfSpeech, List<VariantTranslateModel> variantTranslateModels) {
        this.wordFromTranslate = wordFromTranslate;
        this.partOfSpeech = partOfSpeech;
        this.variantTranslateModels = variantTranslateModels;
    }

    private TranslateModel(Parcel in) {
        wordFromTranslate = in.readString();
        partOfSpeech = in.readString();
    }

    public static final Creator<TranslateModel> CREATOR = new Creator<TranslateModel>() {
        @Override
        public TranslateModel createFromParcel(Parcel in) {
            return new TranslateModel(in);
        }

        @Override
        public TranslateModel[] newArray(int size) {
            return new TranslateModel[size];
        }
    };

    public String getWordFromTranslate() {
        return wordFromTranslate;
    }

    public void setWordFromTranslate(String wordFromTranslate) {
        this.wordFromTranslate = wordFromTranslate;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<VariantTranslateModel> getVariantTranslateModels() {
        return variantTranslateModels;
    }

    public void setVariantTranslateModels(List<VariantTranslateModel> variantTranslateModels) {
        this.variantTranslateModels = variantTranslateModels;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(wordFromTranslate);
        parcel.writeString(partOfSpeech);
    }
}
