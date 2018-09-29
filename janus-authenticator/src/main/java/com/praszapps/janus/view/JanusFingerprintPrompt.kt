/*
 *    Copyright [2018] [Prasannajeet Pani]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.praszapps.janus.view

import android.annotation.SuppressLint
import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.praszapps.janus.R
import com.praszapps.janus.contract.JanusContract
import com.praszapps.janus.model.JanusResponseModel
import com.praszapps.janus.presenter.JanusBiometricPresenter
import kotlinx.android.synthetic.main.fingerprint_dialog.*
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

@SuppressLint("ValidFragment")
internal class JanusFingerprintPrompt(private val liveData: MutableLiveData<JanusResponseModel>) : BottomSheetDialogFragment(), JanusContract.IView {

    override fun getTheme(): Int {
        return R.style.JanusV23BottomSheetDialogTheme
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        retainInstance = true
        return dialog
    }

    private val mPresenter: JanusContract.IPresenter by lazy {
        JanusBiometricPresenter(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fingerprint_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.initialize()
    }


    override fun setUpFingerprintViews() {
        cancel_Button.setOnClickListener {
            mPresenter.cancelFingerprintDetection()
        }
        mPresenter.authenticateViaFingerprint()
    }

    override fun onFingerPrintAuthenticationSuccess() {
        icon_FAB?.setImageResource(R.drawable.ic_check_white_24dp)
        error_TextView?.text = getString(R.string.auth_success_message)
        liveData.value = JanusResponseModel(true)
        dismissAfterHalfSecond()

    }

    override fun onFingerprintAuthenticationFailed(id: Int, text: String) {

        icon_FAB?.setImageResource(R.drawable.ic_error_white_24dp)
        error_TextView?.text = text
        if (id == BiometricPrompt.BIOMETRIC_ERROR_LOCKOUT || id == BiometricPrompt.BIOMETRIC_ERROR_CANCELED) {
            liveData.value = JanusResponseModel(message = text)
            dismissAfterHalfSecond()
        }
    }

    private fun dismissAfterHalfSecond() {
        GlobalScope.launch {
            Thread.sleep(500)
            mPresenter.cancelFingerprintDetection()
            if (this@JanusFingerprintPrompt.isVisible && !this@JanusFingerprintPrompt.isRemoving) {
                dismissAllowingStateLoss()
            }
        }
    }
}