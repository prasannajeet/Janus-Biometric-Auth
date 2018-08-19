package com.praszapps.easyfingerprint.model.di.module;

import com.praszapps.easyfingerprint.view.FingertipActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = FingertipActivityModule.class)
    abstract FingertipActivity bindFingerprintActivity();

}
