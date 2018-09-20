package com.praszapps.fingertip.contract

import android.app.KeyguardManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import com.praszapps.fingertip.model.repository.FingertipInitResponseModel

internal interface FingertipMVPContract {

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
        suspend fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager): FingertipInitResponseModel
        fun startFingerprintTracking(listener: FingerprintManagerCompat.AuthenticationCallback)
        fun stopFingerprintTracking()
    }
}
