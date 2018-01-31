package com.rat.regresapitest.di.global;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.rat.regresapitest.BuildConfig;
import com.rat.regresapitest.data.network.ApiService;
import com.rat.regresapitest.data.network.ErrorHandler;
import com.rat.regresapitest.data.network.NetworkChecker;
import com.rat.regresapitest.data.network.interceptors.NetworkCheckInterceptor;
import com.rat.regresapitest.data.network.interceptors.TokenInterceptor;
import com.rat.regresapitest.data.repositories.ImagesRepositoryImpl;
import com.rat.regresapitest.data.repositories.ShotsRepositoryImpl;
import com.rat.regresapitest.data.repositories.TempPreferencesImpl;
import com.rat.regresapitest.di.global.qualifiers.OkHttpInterceptors;
import com.rat.regresapitest.di.global.qualifiers.OkHttpNetworkInterceptors;
import com.rat.regresapitest.domain.global.repositories.ImagesRepository;
import com.rat.regresapitest.domain.global.repositories.ShotsRepository;
import com.rat.regresapitest.domain.global.repositories.TempPreferences;


import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    private final String baseUrl;

    public DataModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    ShotsRepository provideBookRepository(ShotsRepositoryImpl bookRepository) {
        return bookRepository;
    }

    @Provides
    @Singleton
    ImagesRepository provideImagesRepository(ImagesRepositoryImpl imagesRepository) {
        return imagesRepository;
    }

    @Provides
    @Singleton
    TempPreferences provideTempPreferences(TempPreferencesImpl tempPreferences) {
        return tempPreferences;
    }

    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient(NetworkChecker networkChecker,
                                            @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                            @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new NetworkCheckInterceptor(networkChecker));
        okHttpBuilder.addInterceptor(new TokenInterceptor(BuildConfig.CLIENT_ACCESS_TOKEN));

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        return okHttpBuilder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    ErrorHandler provideErrorHandler(Gson gson) {
        return new ErrorHandler(gson);
    }

    @Provides
    @Singleton
    ApiService provideApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}
