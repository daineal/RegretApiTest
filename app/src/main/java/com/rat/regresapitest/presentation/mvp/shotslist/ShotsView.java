package com.rat.regresapitest.presentation.mvp.shotslist;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.rat.regresapitest.domain.global.models.Shot;
import com.rat.regresapitest.domain.global.models.UserData;
import com.rat.regresapitest.domain.global.models.UsersInfo;

import java.util.List;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface ShotsView extends MvpView {

    void showNewShots(List<UserData> newShots);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showShotsLoadingProgress();
    void hideShotsLoadingProgress();
    void showShotsLoadingMoreProgress();
    void hideShotsLoadingMoreProgress();
    void openShotDetailsScreen(long shotId);
    void showNoNetworkLayout();
    void hideNoNetworkLayout();
    void showLoadMoreError();

}
