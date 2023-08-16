package com.apolis.ecommerceapp.presenter

import com.apolis.ecommerceapp.model.remote.dto.LogoutResponse

interface LogoutContract {

    interface LogoutView {
        fun logoutSuccess(logoutResponse: LogoutResponse)
        fun logoutFailure(error: String)
    }

    interface LogoutPresenter {
        fun performLogout(email: String)
    }
}