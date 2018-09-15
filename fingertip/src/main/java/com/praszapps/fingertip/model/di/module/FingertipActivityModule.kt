package com.praszapps.fingertip.model.di.module

import com.praszapps.fingertip.MVP.FingertipMVPContract
import com.praszapps.fingertip.presenter.FingertipActivityPresenter
import com.praszapps.fingertip.view.FingertipActivity
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FingertipActivityModule {

    @Provides
    @Named("activityView")
    internal fun provideFingerprintView(activity: FingertipActivity): FingertipMVPContract.View {
        return activity
    }

    @Provides
    @Named("activityPresenter")
    internal fun provideFingertipPresenter(): FingertipMVPContract.Presenter {
        return FingertipActivityPresenter()
    }
}