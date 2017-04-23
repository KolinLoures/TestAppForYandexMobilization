package com.sbilsky.data.server;

import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Cвятослав Бильский s.bislky
 */
public interface TranslatorRetrofitInterface {
    String API_KEY_TRNSLATION =
            "trnsl.1.1.20170416T124108Z.02ffdfd229d0ada5.35fcc5e9de87e945533bc1ae30a01189d5994353";
    String BASE_URL_TRANSLATION =
            "https://translate.yandex.net/api/v1.5/tr.json/";

    String API_KEY_DICTIONARY =
            "dict.1.1.20170416T124016Z.4505d498768f4644.eb4d55ed30ac465af3f0aaf01f59d34fe2b59ef3";
    String BASE_URL_DICTIONARY =
            "https://dictionary.yandex.net/api/v1/dicservice.json/lookup";


    @POST("translate")
    Observable<ResponseBody> getTranslate(@Query("key") String key,
                                          @Query(value = "text", encoded = true) String text,
                                          @Query("lang") String lang);


    @POST(BASE_URL_DICTIONARY)
    Observable<ResponseBody> getDictionary(@Query("key") String key,
                                           @Query(value = "text", encoded = true) String text,
                                           @Query("lang") String lang,
                                           @Query("ui") String ui);
}
