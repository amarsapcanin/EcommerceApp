package com.apolis.ecommerceapp.presenter

import com.apolis.ecommerceapp.model.remote.dto.LoginResponse

interface RegistrationContract {

    interface RegistrationView {
        fun registrationSuccess(loginResponse: LoginResponse)
        fun registrationFailure(message: String)
        fun navigateToLoginFragment()
    }

    interface RegistrationPresenter {
        fun performRegistration(
            fullName: String, mobileNo: String, email: String, password: String)
        fun onYesAccountLinkClicked()
    }

}