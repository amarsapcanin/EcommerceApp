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

class RegistrationPresenter(
    private val volleyHandler: VolleyHandler,
    private val registrationView: RegistrationContract.RegistrationView)
    : RegistrationContract.RegistrationPresenter {

    override fun performRegistration( fullName: String, mobileNo: String, email: String,
        password: String) {

        volleyHandler.register(fullName, mobileNo, email, password, object : ResponseCallback{
            override fun successLogin(loginResponse: LoginResponse) {
                registrationView.registrationSuccess(loginResponse)
            }

            override fun successCategory(categoryResponse: CategoryResponse) {
                TODO("Not yet implemented")
            }

            override fun failure(error: String) {
                registrationView.registrationFailure(error)
            }
        })

    }

    override fun onYesAccountLinkClicked() {
        registrationView.navigateToLoginFragment()
    }
}
