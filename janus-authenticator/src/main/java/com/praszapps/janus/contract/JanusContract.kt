package com.praszapps.janus.contract

import android.app.KeyguardManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import com.praszapps.janus.model.repository.JanusInitResponseModel

internal interface JanusContract {

    interface IView {
        fun setUpFingerprintViews()
        fun onFingerPrintAuthenticationSuccess()
        fun onFingerprintAuthenticationFailed(text: String)

    }

    interface IPresenter {
        fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager)
        fun authenticateViaFingerprint()
        fun cancelFingerprintDetection()
    }

    interface IModel {
        suspend fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager): JanusInitResponseModel
        fun startFingerprintTracking(listener: FingerprintManagerCompat.AuthenticationCallback)
        fun stopFingerprintTracking()
    }
}
