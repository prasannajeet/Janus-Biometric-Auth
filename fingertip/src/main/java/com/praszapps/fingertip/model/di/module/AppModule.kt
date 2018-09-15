package com.praszapps.fingertip.model.di.module

import android.content.Context
import com.praszapps.fingertip.view.FingertipApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: FingertipApplication): Context {
        return application.applicationContext
    }

}