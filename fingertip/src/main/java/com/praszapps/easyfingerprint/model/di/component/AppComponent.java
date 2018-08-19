package com.praszapps.easyfingerprint.model.di.component;


import com.praszapps.easyfingerprint.model.di.module.ActivityBuilder;
import com.praszapps.easyfingerprint.model.di.module.AppModule;
import com.praszapps.easyfingerprint.model.di.module.FingertipDialogFragmentModule;
import com.praszapps.easyfingerprint.model.di.module.FingertipRepoModule;
import com.praszapps.easyfingerprint.view.FingertipApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilder.class, FingertipRepoModule.class, FingertipDialogFragmentModule.class})
public interface AppComponent extends AndroidInjector<FingertipApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<FingertipApplication> {
    }

}