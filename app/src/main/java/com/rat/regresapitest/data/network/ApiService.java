package com.rat.regresapitest.data.network;

import com.rat.regresapitest.domain.global.models.Shot;
import com.rat.regresapitest.domain.global.models.UserData;
import com.rat.regresapitest.domain.global.models.UsersInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /* Authenticated user methods */

//    @GET("api/user")
//    Single<User> getAuthenticatedUser();

    /* Shots */

    @GET("api/users")
    Single<UsersInfo> getShots(
            @Query("page") int page,
            @Query("per_page") int pageSize);

    @GET("v1/shots/{shotId}")
    Single<UserData> getShot(@Path("shotId") long id);

    @GET("v1/users/{userId}/shots")
    Single<List<Shot>> getUserShots(@Path("userId") long userId,
                                    @Query("page") int page,
                                    @Query("per_page") int pageSize);

    @GET("v1/user/following/shots")
    Single<List<Shot>> getFollowing(@Query("page") int page,
                                    @Query("per_page") int pageSize);

}
