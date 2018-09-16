package com.praszapps.fingertip.MVP

import android.app.KeyguardManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat

internal interface FingertipMVPContract {

    interface IView {
        fun setUpFingerprintViews()
        fun onFingerPrintAuthenticationSuccess()
        fun onFingerprintAuthenticationFailed(text: String)

    }

    interface IPresenter {
        fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager)
        fun authenticateViaFingerprint()
        fun onViewDestroyed()
    }

    interface IProvider {
        fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager)
        fun startFingerprintTracking()
        fun stopFingerprintTracking()
    }
}
