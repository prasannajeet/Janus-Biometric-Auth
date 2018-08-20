package com.praszapps.fingertip.model.di.module;

import android.content.Context;

import com.praszapps.fingertip.view.FingertipApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module()
public class AppModule {
    @Provides
    @Singleton
    Context provideContext(FingertipApplication application) {
        return application.getApplicationContext();
    }

}