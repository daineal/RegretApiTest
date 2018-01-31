package com.rat.regresapitest.domain.global.repositories;

import com.rat.regresapitest.domain.global.models.Shot;
import com.rat.regresapitest.domain.global.models.ShotsRequestParams;
import com.rat.regresapitest.domain.global.models.UserData;
import com.rat.regresapitest.domain.global.models.UsersInfo;

import java.util.List;

import io.reactivex.Single;

public interface ShotsRepository {

    Single<UsersInfo> getUserInfo(ShotsRequestParams shotsRequestParams);
    Single<List<UserData>> getShots();
    Single<UserData> getShot(long shotId);
}
