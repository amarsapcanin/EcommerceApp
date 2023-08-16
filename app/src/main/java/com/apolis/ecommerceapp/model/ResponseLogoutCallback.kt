package com.apolis.ecommerceapp.model
import com.apolis.ecommerceapp.model.remote.dto.LogoutResponse

interface ResponseLogoutCallback {
    fun successLogout(logoutResponse: LogoutResponse)
    fun failureLogout(error: String)
}