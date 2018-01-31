package com.rat.regresapitest;

import android.content.Context;

import com.rat.regresapitest.data.network.RatApiConstants;
import com.rat.regresapitest.di.global.ApplicationComponent;
import com.rat.regresapitest.di.global.ApplicationModule;
import com.rat.regresapitest.di.global.DaggerApplicationComponent;
import com.rat.regresapitest.di.global.DataModule;

public class RatApplication extends android.app.Application {

    private static ApplicationComponent applicationComponent;
    private static RatApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        applicationComponent = buildComponent();
    }

    public ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule(RatApiConstants.DRIBBBLE_API_URL))
                .build();
    }

    public static Context getInstance() {
        return instance;
    }

    public static ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
