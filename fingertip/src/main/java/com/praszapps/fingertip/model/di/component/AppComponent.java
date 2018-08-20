package com.praszapps.fingertip.model.di.component;

import com.praszapps.fingertip.model.di.module.AppModule;
import com.praszapps.fingertip.model.di.module.FingertipDialogFragmentModule;
import com.praszapps.fingertip.model.di.module.FingertipRepoModule;
import com.praszapps.fingertip.view.FingertipApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, FingertipRepoModule.class, FingertipDialogFragmentModule.class})
public interface AppComponent extends AndroidInjector<FingertipApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<FingertipApplication> {
    }
}