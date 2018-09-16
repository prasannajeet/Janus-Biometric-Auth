package com.praszapps.fingertip.MVP

internal interface ManagerViewInteractor {
    fun onAuthSuccess()
    fun onAuthFailure(message: String)
}