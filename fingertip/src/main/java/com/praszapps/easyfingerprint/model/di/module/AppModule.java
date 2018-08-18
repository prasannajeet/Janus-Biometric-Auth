package com.praszapps.easyfingerprint.model.di.module;

import android.app.Application;
import android.content.Context;

import com.praszapps.easyfingerprint.model.di.component.FingerprintActivityComponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = FingerprintActivityComponent.class)
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

}
