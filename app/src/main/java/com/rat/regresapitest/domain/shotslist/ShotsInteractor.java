package com.rat.regresapitest.domain.shotslist;


import com.rat.regresapitest.domain.global.models.Shot;
import com.rat.regresapitest.domain.global.models.ShotsRequestParams;
import com.rat.regresapitest.domain.global.models.UsersInfo;
import com.rat.regresapitest.domain.global.repositories.ShotsRepository;
import com.rat.regresapitest.presentation.mvp.global.SchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsInteractor {

    private ShotsRepository shotsRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public ShotsInteractor(ShotsRepository shotsRepository,
                           SchedulersProvider schedulersProvider) {
        this.shotsRepository = shotsRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<UsersInfo> getShots(ShotsRequestParams shotsRequestParams) {
        return shotsRepository.getUserInfo(shotsRequestParams)
                .subscribeOn(schedulersProvider.io());
    }

}
