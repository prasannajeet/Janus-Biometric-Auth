package com.praszapps.fingertip.MVP

import android.app.KeyguardManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import com.praszapps.fingertip.model.repository.ErrorModel
import com.praszapps.fingertip.presenter.FingertipDialogFragmentPresenter
import io.reactivex.Observable

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

    interface IProvider {
        fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager): Observable<ErrorModel>
        fun startFingerprintTracking(callback: FingertipDialogFragmentPresenter.FingerprintResultCallback)
        fun stopFingerprintTracking()
    }
}
