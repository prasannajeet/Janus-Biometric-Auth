package com.praszapps.fingertip.manager

import android.support.annotation.Keep

/**
 * Denotes various fingeprint authentication styles supported by the library
 * @author Prasannajeet Pani
 * @since 0.2.0
 */
@Keep
enum class AuthenticationStyle {
    FINGERPRINT_DIALOG,
    //FINGERPRINT_ACTIVITY,
    //DEVICE_LOCK
}