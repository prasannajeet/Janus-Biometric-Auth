package com.praszapps.fingertip.manager

import android.content.Context
import android.support.annotation.Keep

@Keep
data class FingertipAuthConfig(val context: Context, val mAuthStyle: AuthenticationStyle)