package com.praszapps.fingertip.contract

import android.support.annotation.Keep

@Keep
internal interface ManagerViewInteractor {
    fun onAuthSuccess()
    fun onAuthFailure(message: String)
}