package com.rat.regresapitest.di.shotslist;

import com.rat.regresapitest.di.global.scopes.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ShotsPresenterModule {

    private final String sortType;

    public ShotsPresenterModule(String sortType) {
        this.sortType = sortType;
    }

    @Provides
    @Presenter
    String provideSortType() {
        return sortType;
    }

}
