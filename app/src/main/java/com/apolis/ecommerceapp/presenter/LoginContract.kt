package com.apolis.ecommerceapp.presenter

import com.apolis.ecommerceapp.model.remote.dto.LoginResponse

interface LoginContract {
    interface LoginView {
        fun loginSuccess(loginResponse: LoginResponse)
        fun loginFailure(error: String)
        fun navigateToRegistrationFragment()
    }

    interface LoginPresenter {
        fun performLogin(email: String, password: String)
        fun onNoAccountLinkClicked()
    }
}