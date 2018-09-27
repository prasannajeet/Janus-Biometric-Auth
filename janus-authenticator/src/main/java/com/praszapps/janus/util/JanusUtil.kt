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

package com.praszapps.janus.util

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.lifecycle.MutableLiveData
import com.praszapps.janus.model.JanusResponseModel
import com.praszapps.janus.view.JanusFingerprintPrompt

@TargetApi(23)
/**
 * Util class
 */
object JanusUtil {

    internal lateinit var fManager: FingerprintManagerCompat
    private lateinit var kManager: KeyguardManager
    private lateinit var supportFragmentManager: androidx.fragment.app.FragmentManager
    internal val DEFAULT_KEY_NAME = "JanusKeyName"
    internal val tag = "fingerprintdialogTag"

    /**
     * Helper function to inform application if Fingertip supports authentication
     * @return true if supports, false otherwise
     */
    internal fun isSupportFingerprintAuthentication(context: Context): Boolean {
        val packageManager: PackageManager = context.packageManager
        supportFragmentManager = (context as AppCompatActivity).supportFragmentManager
        fManager = FingerprintManagerCompat.from(context)
        kManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) && fManager.isHardwareDetected && fManager.hasEnrolledFingerprints() && kManager.isDeviceSecure && kManager.isKeyguardSecure && isApiLevelSupported()
    }

    internal fun showBiometricDialog(successLiveData: MutableLiveData<JanusResponseModel>) {
        val fingerDialog = JanusFingerprintPrompt()
        fingerDialog.liveData = successLiveData
        fingerDialog.fragmentManager = supportFragmentManager
        fingerDialog.initialize()
    }

    private fun isApiLevelSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}