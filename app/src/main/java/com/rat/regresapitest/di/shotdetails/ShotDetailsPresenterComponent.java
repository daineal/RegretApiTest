package com.rat.regresapitest.di.shotdetails;

import com.rat.regresapitest.di.global.ApplicationComponent;
import com.rat.regresapitest.di.global.scopes.Presenter;
import com.rat.regresapitest.presentation.mvp.shotdetails.ShotDetailsPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = ShotDetailsPresenterModule.class)
public interface ShotDetailsPresenterComponent {

    ShotDetailsPresenter getPresenter();

}
