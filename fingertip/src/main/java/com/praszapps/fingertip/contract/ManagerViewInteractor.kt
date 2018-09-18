package com.praszapps.fingertip.contract

internal interface ManagerViewInteractor {
    fun onAuthSuccess()
    fun onAuthFailure(message: String)
}