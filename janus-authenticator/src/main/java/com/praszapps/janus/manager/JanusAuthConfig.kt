package com.praszapps.janus.manager

import android.content.Context
import android.support.annotation.Keep

@Keep
data class JanusAuthConfig(val context: Context, val mAuthStyle: AuthenticationStyle)