package com.apolis.ecommerceapp.presenter

import com.apolis.ecommerceapp.model.ResponseLoginCallback
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.remote.dto.CategoryResponse
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse

class RegistrationPresenter(
    private val volleyHandler: VolleyHandler,
    private val registrationView: RegistrationContract.RegistrationView)
    : RegistrationContract.RegistrationPresenter {

    override fun performRegistration( fullName: String, mobileNo: String, email: String,
        password: String) {

        volleyHandler.register(fullName, mobileNo, email, password, object : ResponseLoginCallback{
            override fun successLogin(loginResponse: LoginResponse) {
                registrationView.registrationSuccess(loginResponse)
            }

            override fun failureLogin(error: String) {
                registrationView.registrationFailure(error)
            }
        })

    }

    override fun onYesAccountLinkClicked() {
        registrationView.navigateToLoginFragment()
    }
}
