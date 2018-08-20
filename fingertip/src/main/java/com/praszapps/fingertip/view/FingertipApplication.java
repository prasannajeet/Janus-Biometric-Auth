package com.praszapps.fingertip.view;


import com.praszapps.fingertip.model.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public abstract class FingertipApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends FingertipApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}