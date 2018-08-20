package com.praszapps.fingertip.model.di.module;

import com.praszapps.fingertip.model.repository.FingertipRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FingertipRepoModule {

    @Provides
    @Singleton
    public FingertipRepository provideRepository() {
        return new FingertipRepository();
    }
}