package com.rat.regresapitest.presentation.mvp.shotdetails;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.rat.regresapitest.domain.global.exceptions.NoNetworkException;
import com.rat.regresapitest.domain.global.models.Shot;
import com.rat.regresapitest.domain.global.models.UserData;
import com.rat.regresapitest.domain.shotdetails.ShotDetailsInteractor;
import com.rat.regresapitest.presentation.mvp.global.SchedulersProvider;
import com.rat.regresapitest.presentation.mvp.global.permissions.Permission;
import com.rat.regresapitest.presentation.mvp.global.permissions.PermissionsManager;
import com.rat.regresapitest.presentation.mvp.global.permissions.PermissionsManagerHolder;
import com.rat.regresapitest.presentation.utils.DebugUtils;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class ShotDetailsPresenter extends MvpPresenter<ShotDetailsView> {

    private static final int COMMENTS_PAGE_SIZE = 20;

    private ShotDetailsInteractor shotDetailsInteractor;
    private SchedulersProvider schedulersProvider;
    private PermissionsManagerHolder permissionsManagerHolder;
    private long shotId;
    private UserData shot;
    private int currentMaxCommentsPage = 1;
    private boolean isCommentsLoading = false;

    @Inject
    public ShotDetailsPresenter(ShotDetailsInteractor shotDetailsInteractor,
                                SchedulersProvider schedulersProvider,
                                long shotId) {
        this.shotDetailsInteractor = shotDetailsInteractor;
        this.permissionsManagerHolder = new PermissionsManagerHolder();
        this.schedulersProvider = schedulersProvider;
        this.shotId = shotId;
    }

    public void setPermissionsManager(PermissionsManager permissionsManager) {
        permissionsManagerHolder.setPermissionsManager(permissionsManager);
    }

    public void removePermissionsManager() {
        permissionsManagerHolder.removePermissionsManager();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadShot();
    }

    private void loadShot() {
        getViewState().showLoadingProgress();
        shotDetailsInteractor.getShot(shotId)
                .observeOn(schedulersProvider.ui())
                .subscribe(this::onShotLoaded, this::onShotLoadError);
    }

    private void onShotLoaded(UserData shot) {
        this.shot = shot;
        getViewState().hideLoadingProgress();
        getViewState().showShot(shot);

    }

    private void onShotLoadError(Throwable throwable) {
        if (throwable instanceof NoNetworkException) {
            getViewState().hideLoadingProgress();
            getViewState().showNoNetworkLayout();
        } else {
            DebugUtils.showDebugErrorMessage(throwable);
        }
    }

    public void onImageLoadError() {
        getViewState().hideImageLoadingProgress();
    }

    public void onImageLoadSuccess() {
        getViewState().hideImageLoadingProgress();
    }

    public void retryLoading() {
        getViewState().hideNoNetworkLayout();
        loadShot();
    }


    public void onLoadMoreCommentsRequest() {
        if (isCommentsLoading) {
            return;
        }
        currentMaxCommentsPage++;
    }

    public void onImageClicked() {
//        getViewState().openShotImageScreen(shot);
    }

    public void onLikeShotClicked() {

    }

    public void onDownloadImageClicked() {
        if (permissionsManagerHolder.checkPermissionGranted(Permission.READ_EXTERNAL_STORAGE)) {
            saveShotImage();
        } else {
            permissionsManagerHolder.requestPermission(Permission.READ_EXTERNAL_STORAGE, permissionResult -> {
                if (permissionResult.granted) {
                    saveShotImage();
                } else if (permissionResult.shouldShowRequestPermissionRationale) {
                    getViewState().showStorageAccessRationaleMessage();
                } else {
                    getViewState().showAllowStorageAccessMessage();
                }
            });
        }
    }

    public void onAppSettingsButtonClicked() {
        getViewState().openAppSettingsScreen();
    }

    private void saveShotImage() {
        shotDetailsInteractor.saveImage(shot.getAvatar())
                .observeOn(schedulersProvider.ui())
                .subscribe(() -> getViewState().showImageSavedMessage(), DebugUtils::showDebugErrorMessage);
    }

    public void onTagClicked(String tag) {

    }

}
