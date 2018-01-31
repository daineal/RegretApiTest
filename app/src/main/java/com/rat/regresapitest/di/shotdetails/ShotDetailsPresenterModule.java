package com.rat.regresapitest.di.shotdetails;

import com.rat.regresapitest.di.global.scopes.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ShotDetailsPresenterModule {

    private final long shotId;

    public ShotDetailsPresenterModule(long shotId) {
        this.shotId = shotId;
    }

    @Provides
    @Presenter
    long provideShotId() {
        return shotId;
    }

}
