package com.rat.regresapitest.domain.global.repositories;


import com.rat.regresapitest.domain.global.models.User;
import com.rat.regresapitest.domain.global.models.UserData;
import com.rat.regresapitest.domain.global.models.UsersInfo;
import com.rat.regresapitest.domain.global.models.UsersRequestParams;

import java.util.List;

import io.reactivex.Single;

public interface UsersRepository {

    Single<UsersInfo> getUserInfo(UsersRequestParams usersRequestParams);
    Single<List<UserData>> getUserData();
    Single<UserData> getUser(long userId);

}
