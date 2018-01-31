package com.rat.regresapitest.presentation.mvp.shotslist;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.rat.regresapitest.domain.global.exceptions.NoNetworkException;
import com.rat.regresapitest.domain.global.models.Shot;
import com.rat.regresapitest.domain.global.models.ShotsRequestParams;
import com.rat.regresapitest.domain.global.models.UserData;
import com.rat.regresapitest.domain.global.models.UsersInfo;
import com.rat.regresapitest.domain.shotslist.ShotsInteractor;
import com.rat.regresapitest.presentation.mvp.global.SchedulersProvider;
import com.rat.regresapitest.presentation.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class ShotsPresenter extends MvpPresenter<ShotsView> {

    private static final int PAGE_SIZE = 20;
    private static final int MAX_PAGE_NUMBER = 25;

    private ShotsInteractor shotsInteractor;
    private SchedulersProvider schedulersProvider;
    private String shotsSort;
    private int currentMaxPage = 1;
    private List<UserData> shots = new ArrayList<>();
    private boolean isShotsLoading = false;

    @Inject
    public ShotsPresenter(ShotsInteractor shotsInteractor, SchedulersProvider schedulersProvider, String shotsSort) {
        this.shotsInteractor = shotsInteractor;
        this.schedulersProvider = schedulersProvider;
        this.shotsSort = shotsSort;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showShotsLoadingProgress();
        loadMoreShots(currentMaxPage);
    }

    private void loadMoreShots(int page) {
        isShotsLoading = true;
        ShotsRequestParams shotsRequestParams = new ShotsRequestParams(shotsSort, page);
        shotsInteractor.getShots(shotsRequestParams)
                .observeOn(schedulersProvider.ui())
                .subscribe(this::onShotsLoaded, this::onShotsLoadError);
    }

    private void onShotsLoaded(UsersInfo newShots) {
        isShotsLoading = false;
        if (isFirstLoading()) {
            getViewState().hideShotsLoadingProgress();
        } else {
            getViewState().hideShotsLoadingMoreProgress();
        }

        shots.addAll(newShots.getData());
        getViewState().showNewShots(newShots.getData());
    }

    private void onShotsLoadError(Throwable throwable) {
        isShotsLoading = true;
        if (throwable instanceof NoNetworkException) {
            if (isFirstLoading()) {
                getViewState().hideShotsLoadingProgress();
                getViewState().showNoNetworkLayout();
            } else {
                getViewState().hideShotsLoadingMoreProgress();
                getViewState().showLoadMoreError();
            }
        } else {
            DebugUtils.showDebugErrorMessage(throwable);
        }
    }

    private boolean isFirstLoading() {
        return currentMaxPage == 1;
    }

    public void onLoadMoreShotsRequest() {
        if (isShotsLoading) {
            return;
        }

        if (currentMaxPage < MAX_PAGE_NUMBER) {
            getViewState().showShotsLoadingMoreProgress();
            currentMaxPage++;
            loadMoreShots(currentMaxPage);
        }
    }

    public void retryLoading() {
        if (isFirstLoading()) {
            getViewState().hideNoNetworkLayout();
            getViewState().showShotsLoadingProgress();
        } else {
            getViewState().showShotsLoadingMoreProgress();
        }

        loadMoreShots(currentMaxPage);
    }

    public void onShotClick(int position) {
        getViewState().openShotDetailsScreen(shots.get(position).getId());
    }

}
