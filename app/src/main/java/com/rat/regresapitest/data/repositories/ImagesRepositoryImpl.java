package com.rat.regresapitest.data.repositories;

import com.rat.regresapitest.data.filesystem.UrlImageSaver;
import com.rat.regresapitest.domain.global.repositories.ImagesRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ImagesRepositoryImpl implements ImagesRepository {

    private UrlImageSaver urlImageSaver;

    @Inject
    public ImagesRepositoryImpl(UrlImageSaver urlImageSaver) {
        this.urlImageSaver = urlImageSaver;
    }

    public Completable saveImage(String shotImageUrl) {
        return Completable.create(e -> {
            urlImageSaver.saveImage(shotImageUrl);
            e.onComplete();
        });
    }

}
