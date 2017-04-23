package com.sbilsky.data.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import static com.sbilsky.data.server.RetrofitSingleton.initRetrofit;
import static com.sbilsky.data.storage.api.sql.SQLDataBaseHelper.initDBHelper;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class YandexTestAppApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initRetrofit();
        initDBHelper(getApplicationContext());
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }

    public static Context getAppContext() {
        return context;
    }


}
