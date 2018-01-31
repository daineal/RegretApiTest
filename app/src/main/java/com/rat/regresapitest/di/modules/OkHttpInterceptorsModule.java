package com.rat.regresapitest.di.modules;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rat.regresapitest.di.global.qualifiers.OkHttpInterceptors;
import com.rat.regresapitest.di.global.qualifiers.OkHttpNetworkInterceptors;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

import static java.util.Collections.singletonList;

@Module
public class OkHttpInterceptorsModule {

    @Provides
    @OkHttpInterceptors
    @Singleton
    @NonNull
    public List<Interceptor> provideOkHttpInterceptors() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return singletonList(httpLoggingInterceptor);
    }

    @Provides @OkHttpNetworkInterceptors
    @Singleton @NonNull
    public List<Interceptor> provideOkHttpNetworkInterceptors() {
        return singletonList(new StethoInterceptor());
    }

}
