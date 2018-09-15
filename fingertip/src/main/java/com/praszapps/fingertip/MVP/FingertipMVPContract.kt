package com.praszapps.fingertip.MVP

interface FingertipMVPContract {

    interface View {
        fun setUpFingerprintViews()
        fun handleNoFingerprintAuthPossible()
        fun onFingerPrintAuthenticationSuccess()
        fun onFingerprintAuthenticationFailed(text: String)

    }

    interface Presenter {
        fun authenticateViaFingerprint()
        fun stopFingerprintTracking()
    }

    interface IModel {
        fun getStandardErrorText()
    }

    interface IFingerprintRepository {
        val isFingerprintAvailable: Boolean
        fun initalize()
        fun startFingerprintTracking()
        fun stopFingerprintTracking()
    }
}
