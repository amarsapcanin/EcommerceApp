package com.apolis.ecommerceapp.presenter

interface LoginContract {
    interface LoginView {
        fun showLoginSuccess()
        fun showLoginFailure(message: String)
        fun navigateToRegistrationFragment()
    }

    interface LoginPresenter {
        fun performLogin(email: String, password: String)
        fun onNoAccountLinkClicked()
    }
}