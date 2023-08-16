package com.apolis.ecommerceapp.presenter

import com.apolis.ecommerceapp.model.ResponseLoginCallback
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.remote.dto.CategoryResponse
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse

class LoginPresenter(private val volleyHandler: VolleyHandler,
                     private val loginView: LoginContract.LoginView) :
    LoginContract.LoginPresenter {

    override fun performLogin(email: String, password: String) {
        volleyHandler.login(email, password, responseLoginCallback = object : ResponseLoginCallback{

            override fun successLogin(loginResponse: LoginResponse) {
                loginView.loginSuccess(loginResponse)
            }

            override fun failureLogin(error: String) {
                loginView.loginFailure(error)
            }
        })
    }

    override fun onNoAccountLinkClicked() {
        loginView.navigateToRegistrationFragment()
    }
}
