package com.rat.regresapitest.domain.shotdetails;



import com.rat.regresapitest.domain.global.models.Shot;
import com.rat.regresapitest.domain.global.models.UserData;
import com.rat.regresapitest.domain.global.repositories.ImagesRepository;
import com.rat.regresapitest.domain.global.repositories.ShotsRepository;
import com.rat.regresapitest.presentation.mvp.global.SchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ShotDetailsInteractor {

    private ShotsRepository shotsRepository;
    private ImagesRepository imagesRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public ShotDetailsInteractor(ShotsRepository shotsRepository,
                                 ImagesRepository imagesRepository,
                                 SchedulersProvider schedulersProvider) {
        this.shotsRepository = shotsRepository;
        this.imagesRepository = imagesRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<UserData> getShot(long shotId) {
        return shotsRepository.getShot(shotId)
                .subscribeOn(schedulersProvider.io());
    }

    public Completable saveImage(String imageUrl) {
        return imagesRepository.saveImage(imageUrl)
                .subscribeOn(schedulersProvider.io());
    }

}
