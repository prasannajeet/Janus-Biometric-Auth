package com.praszapps.fingertip.model.di.component

import com.praszapps.fingertip.model.di.module.AppModule
import com.praszapps.fingertip.model.di.module.FingertipDialogFragmentModule
import com.praszapps.fingertip.model.di.module.FingertipRepoModule
import com.praszapps.fingertip.view.FingertipApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, AppModule::class, FingertipRepoModule::class, FingertipDialogFragmentModule::class))
interface AppComponent : AndroidInjector<FingertipApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<FingertipApplication>()
}