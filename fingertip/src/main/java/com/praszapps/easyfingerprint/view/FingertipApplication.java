package com.praszapps.easyfingerprint.view;


import com.praszapps.easyfingerprint.model.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public abstract class FingertipApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends FingertipApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}