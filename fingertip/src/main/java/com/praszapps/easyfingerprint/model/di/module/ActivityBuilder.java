package com.praszapps.easyfingerprint.model.di.module;

import android.app.Activity;

import com.praszapps.easyfingerprint.model.di.component.FingerprintActivityComponent;
import com.praszapps.easyfingerprint.view.FingerprintActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(FingerprintActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindFingerprintActivity(FingerprintActivityComponent.Builder builder);

}
