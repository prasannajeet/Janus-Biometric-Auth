package com.praszapps.fingertip.view


import com.praszapps.fingertip.model.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

abstract class FingertipApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out FingertipApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}