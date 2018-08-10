package com.praszapps.easyfingerprint.model.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule (Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    public Context getContext() {
        return this.context;
    }
}
