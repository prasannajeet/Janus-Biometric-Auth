package com.praszapps.easyfingerprint.model.di.component;

import android.app.Application;

import com.praszapps.easyfingerprint.model.di.module.ActivityBuilder;
import com.praszapps.easyfingerprint.model.di.module.AppModule;
import com.praszapps.easyfingerprint.model.di.module.FingerprintRepoModule;
import com.praszapps.easyfingerprint.view.FingertipApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class, FingerprintRepoModule.class})
public interface AppComponent {

    void inject(FingertipApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

}