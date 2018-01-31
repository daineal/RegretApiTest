package com.rat.regresapitest.domain.global.repositories;


import io.reactivex.Completable;

public interface ImagesRepository {

    Completable saveImage(String shotImageUrl);

}
