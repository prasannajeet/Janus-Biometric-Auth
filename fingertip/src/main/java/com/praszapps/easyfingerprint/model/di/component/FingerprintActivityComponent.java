package com.praszapps.easyfingerprint.model.di.component;

import com.praszapps.easyfingerprint.model.di.module.FingerprintActivityModule;
import com.praszapps.easyfingerprint.view.FingerprintActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = FingerprintActivityModule.class)
public interface FingerprintActivityComponent extends AndroidInjector<FingerprintActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<FingerprintActivity> {
    }
}
