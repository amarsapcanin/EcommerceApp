package com.apolis.ecommerceapp.presenter

import com.apolis.ecommerceapp.model.ResponseLogoutCallback
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.remote.dto.LogoutResponse

class LogoutPresenter(private val volleyHandler: VolleyHandler,
    private val logoutView: LogoutContract.LogoutView) :
LogoutContract.LogoutPresenter{
    override fun performLogout(email: String) {
        volleyHandler.logout(email, object : ResponseLogoutCallback{
            override fun successLogout(logoutResponse: LogoutResponse) {
                logoutView.logoutSuccess(logoutResponse)
            }

            override fun failureLogout(error: String) {
                logoutView.logoutFailure(error)
            }

        })
    }
}