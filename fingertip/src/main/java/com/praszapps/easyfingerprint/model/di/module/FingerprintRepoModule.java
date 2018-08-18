package com.praszapps.easyfingerprint.model.di.module;

import android.content.Context;

import com.praszapps.easyfingerprint.model.repository.FingerPrintRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class FingerprintRepoModule {

    @Provides
    public FingerPrintRepository provideRepository(Context context) {
        return FingerPrintRepository.getInstance(context);
    }

}