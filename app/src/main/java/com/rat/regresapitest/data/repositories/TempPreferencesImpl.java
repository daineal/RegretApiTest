package com.rat.regresapitest.data.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import com.rat.regresapitest.BuildConfig;
import com.rat.regresapitest.domain.global.repositories.TempPreferences;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class TempPreferencesImpl implements TempPreferences {

    private static final String APP_PREFS_FILE_NAME = "app_preferences";
    private static final String PREF_API_TOKEN = "api_token";

    private SharedPreferences prefs;

    @Inject
    public TempPreferencesImpl(Context context) {
        this.prefs = context.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public Completable saveToken(String token) {
        return Completable.fromAction(() -> prefs.edit().putString(PREF_API_TOKEN, token).apply());
    }

    @Override
    public Single<String> getToken() {
        return Single.fromCallable(() -> prefs.getString(PREF_API_TOKEN, BuildConfig.CLIENT_ACCESS_TOKEN));
    }

    @Override
    public Completable clearToken() {
        return Completable.fromAction(() -> prefs.edit().putString(PREF_API_TOKEN, null).apply());

    }

}
