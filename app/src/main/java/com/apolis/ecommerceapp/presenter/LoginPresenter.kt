package com.apolis.ecommerceapp.presenter

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apolis.ecommerceapp.model.ResponseCallback
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.remote.dto.CategoryResponse
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse
import com.google.gson.Gson
import org.json.JSONObject

class LoginPresenter(private val volleyHandler: VolleyHandler, private val loginView: LoginContract.LoginView) :
    LoginContract.LoginPresenter {

    override fun performLogin(email: String, password: String) {
        volleyHandler.login(email, password, responseCallback = object : ResponseCallback{

            override fun successLogin(loginResponse: LoginResponse) {
                loginView.loginSuccess(loginResponse)
            }

            override fun successCategory(categoryResponse: CategoryResponse) {
                TODO("Not yet implemented")
            }

            override fun failure(error: String) {
                loginView.loginFailure(error)
            }
        })
    }

    override fun onNoAccountLinkClicked() {
        loginView.navigateToRegistrationFragment()
    }
}
