package com.praszapps.easyfingerprint.model.di.module;

import android.content.Context;

import com.praszapps.easyfingerprint.model.repository.FingertipRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class FingertipRepoModule {

    @Provides
    public FingertipRepository provideRepository(Context context) {
        return FingertipRepository.getInstance(context);
    }
}