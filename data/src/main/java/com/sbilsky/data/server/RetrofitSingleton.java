package com.sbilsky.data.server;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class RetrofitSingleton {
    private static Retrofit retrofit;

    public static void initRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(TranslatorRetrofitInterface.BASE_URL_TRANSLATION)
                    .build();
        }
    }

    public static Retrofit getInstance() {
        initRetrofit();
        return retrofit;
    }

}
