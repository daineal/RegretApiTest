package com.rat.regresapitest.di.global;

import com.rat.regresapitest.di.modules.OkHttpInterceptorsModule;
import com.rat.regresapitest.domain.global.repositories.ImagesRepository;
import com.rat.regresapitest.domain.global.repositories.ShotsRepository;
import com.rat.regresapitest.domain.global.repositories.UsersRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, OkHttpInterceptorsModule.class})
public interface ApplicationComponent {

    ShotsRepository provideBookRepository();

//    UsersRepository provideUsersRepository();

    ImagesRepository provideImagesRepository();

}
