package com.praszapps.fingertip.presenter


import android.app.KeyguardManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import com.praszapps.fingertip.MVP.FingertipMVPContract
import com.praszapps.fingertip.model.repository.FingertipSecureProvider
import com.praszapps.fingertip.model.repository.ErrorModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

internal class FingertipDialogFragmentPresenter(IView: FingertipMVPContract.IView) : FingertipMVPContract.IPresenter {

    private val mView = IView

    private val mFingerprintRepository = FingertipSecureProvider()

    private lateinit var observable:Observable<ErrorModel>

    override fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager) {
        mFingerprintRepository.initialize(mFingerprintManager, mKeyguardManager)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    if (it.isSuccess) {
                        mView.setUpFingerprintViews()
                        observable = Observable.create {
                            subscriber ->
                            subscriber.onNext(ErrorModel())
                            subscriber.onComplete()
                        }


                    } else {
                        mView.onFingerprintAuthenticationFailed(it.message)
                    }
                }
    }

    override fun authenticateViaFingerprint() {
        mFingerprintRepository.startFingerprintTracking(object : FingerprintResultCallback {
            override fun onSuccess() {
                mView.onFingerPrintAuthenticationSuccess()
            }

            override fun onFailed(message: String) {
                mView.onFingerprintAuthenticationFailed(message)
            }
        })
    }

    override fun cancelFingerprintDetection() {
        mFingerprintRepository.stopFingerprintTracking()
    }

    interface FingerprintResultCallback {
        fun onSuccess()
        fun onFailed(message: String)
    }
}