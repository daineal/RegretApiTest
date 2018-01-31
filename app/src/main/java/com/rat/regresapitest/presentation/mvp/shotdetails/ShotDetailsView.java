package com.rat.regresapitest.presentation.mvp.shotdetails;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.rat.regresapitest.domain.global.models.Shot;
import com.rat.regresapitest.domain.global.models.UserData;

import java.util.List;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface ShotDetailsView extends MvpView {

    void showShot(UserData shot);
    void showLoadingProgress();
    void hideLoadingProgress();
    void showNoNetworkLayout();
    void hideNoNetworkLayout();
    void hideImageLoadingProgress();
    void showImageSavedMessage();
    void showStorageAccessRationaleMessage();
    void showAllowStorageAccessMessage();
    void openAppSettingsScreen();

}
