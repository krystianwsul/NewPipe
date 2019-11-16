package com.example.discoverfreedom;

import android.content.Context;
import android.preference.PreferenceManager;

import androidx.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.android.gms.ads.MobileAds;

import org.schabi.newpipe.extractor.Downloader;

import okhttp3.OkHttpClient;

public class DebugApp extends App {
    private static final String TAG = DebugApp.class.toString();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initStetho();

        MobileAds.initialize(this);
    }

    @Override
    protected Downloader getDownloader() {
        return com.example.discoverfreedom.Downloader.init(new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor()));
    }

    private void initStetho() {
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(getApplicationContext())
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }

    @Override
    protected boolean isDisposedRxExceptionsReported() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(getString(R.string.allow_disposed_exceptions_key), false);
    }
}
