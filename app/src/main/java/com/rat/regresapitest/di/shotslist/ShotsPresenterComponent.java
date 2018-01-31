package com.rat.regresapitest.di.shotslist;

import com.rat.regresapitest.di.global.ApplicationComponent;
import com.rat.regresapitest.di.global.scopes.Presenter;
import com.rat.regresapitest.presentation.mvp.shotslist.ShotsPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = ShotsPresenterModule.class)
public interface ShotsPresenterComponent {

    ShotsPresenter getPresenter();

}
