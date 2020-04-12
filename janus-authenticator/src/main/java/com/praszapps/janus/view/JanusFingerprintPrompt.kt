/*
 *    Copyright 2018 Prasannajeet Pani
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.praszapps.janus.R
import com.praszapps.janus.model.JanusResponseModel
import com.praszapps.janus.model.JanusResult
import com.praszapps.janus.viewmodel.JanusDialogViewModel
import kotlinx.android.synthetic.main.fingerprint_dialog.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("ValidFragment")
internal class JanusFingerprintPrompt(private val liveData: MutableLiveData<JanusResponseModel>) : BottomSheetDialogFragment() {


    private val viewModel: JanusDialogViewModel by lazy {
        ViewModelProviders.of(this).get(JanusDialogViewModel::class.java)
    }

    override fun getTheme(): Int {
        return R.style.JanusV23BottomSheetDialogTheme
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        retainInstance = true
        return dialog
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fingerprint_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = viewModel.initiateFingerprintAuthentication()
        when (result) {
            is JanusResult.Success -> {
                /*if(result.successObject.isKeyInvalidated) {
                    liveData.value = JanusResponseModel(false, true)
                    dismissImmediately()
                } else {*/
                    setUpFingerprintViews()
                //}
            }
            is JanusResult.Error -> liveData.value = JanusResponseModel(message = result.exception.localizedMessage)
        }
    }

    private fun setUpFingerprintViews() {
        cancel_Button.setOnClickListener {
            viewModel.cancelFingerprintDetection()
        }

        viewModel.authenticateViaFingerprint().observe(viewLifecycleOwner, Observer { model ->
            if (model.isSuccess) {
                onFingerPrintAuthenticationSuccess()
            } else {
                onFingerprintAuthenticationFailed(model.messageId, model.message)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelFingerprintDetection()
    }

    private fun onFingerPrintAuthenticationSuccess() {
        icon_FAB?.setImageResource(R.drawable.ic_check_white_24dp)
        error_TextView?.text = getString(R.string.auth_success_message)
        liveData.value = JanusResponseModel(true)
        dismissAfter200Mils()

    }

    private fun onFingerprintAuthenticationFailed(id: Int, text: String) {

        icon_FAB?.setImageResource(R.drawable.ic_error_white_24dp)
        error_TextView?.text = text
        if (id == BiometricPrompt.BIOMETRIC_ERROR_LOCKOUT || id == BiometricPrompt.BIOMETRIC_ERROR_CANCELED) {
            liveData.value = JanusResponseModel(message = text)
            dismissAfter200Mils()
        }
    }

    private fun dismissAfter200Mils() {
        GlobalScope.launch {
            Thread.sleep(200)
            viewModel.cancelFingerprintDetection()
            if (this@JanusFingerprintPrompt.isVisible && !this@JanusFingerprintPrompt.isRemoving) {
                dismissAllowingStateLoss()
            }
        }
    }

    private fun dismissImmediately() {
        GlobalScope.launch {
            viewModel.cancelFingerprintDetection()
            dismissAllowingStateLoss()
        }
    }
}