package com.rat.regresapitest.data.repositories;

import com.rat.regresapitest.data.network.ApiService;
import com.rat.regresapitest.domain.global.models.Shot;
import com.rat.regresapitest.domain.global.models.ShotsRequestParams;
import com.rat.regresapitest.domain.global.models.UserData;
import com.rat.regresapitest.domain.global.models.UsersInfo;
import com.rat.regresapitest.domain.global.repositories.ShotsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsRepositoryImpl implements ShotsRepository {

    private ApiService apiService;

    @Inject
    public ShotsRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Single<UsersInfo> getUserInfo(ShotsRequestParams shotsRequestParams) {
        return apiService.getShots(shotsRequestParams.getPage(), shotsRequestParams.getPageSize());
    }

    @Override
    public Single<List<UserData>> getShots() {
        return null;
    }

    @Override
    public Single<UserData> getShot(long shotId) {
        return apiService.getShot(shotId);
    }


}
