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

package com.praszapps.janus.util

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

@TargetApi(23)
/**
 * Util class
 */
object JanusUtil {

    internal lateinit var fManager: FingerprintManagerCompat
    private lateinit var kManager: KeyguardManager
    internal const val DEFAULT_KEY_NAME = "JanusKeyName"
    internal const val tag = "fingerprintdialogTag"

    /**
     * Helper function to inform application if Fingertip supports authentication
     * @return true if supports, false otherwise
     */
    internal fun initiate(context: Context): Boolean {
        val packageManager: PackageManager = context.packageManager
        fManager = FingerprintManagerCompat.from(context)
        kManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) && fManager.isHardwareDetected && fManager.hasEnrolledFingerprints() && kManager.isDeviceSecure && kManager.isKeyguardSecure && isApiLevelSupported()
    }

    private fun isApiLevelSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}