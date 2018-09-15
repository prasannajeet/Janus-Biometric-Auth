package com.praszapps.fingertip.model.di.module

import com.praszapps.fingertip.view.FingertipActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(FingertipActivityModule::class))
    internal abstract fun bindFingerprintActivity(): FingertipActivity

}
