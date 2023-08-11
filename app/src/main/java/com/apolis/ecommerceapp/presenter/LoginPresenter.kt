package com.apolis.ecommerceapp.presenter

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse
import com.google.gson.Gson
import org.json.JSONObject

class LoginPresenter(private val loginView: LoginContract.LoginView, private val context: Context) :
    LoginContract.LoginPresenter {

    override fun performLogin(email: String, password: String) {
        val jsonObject = JSONObject()
        jsonObject.put("email_id", email)
        jsonObject.put("password", password)

        val url = "http://10.0.2.2/myshop/index.php/User/auth"

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                val loginResponse = Gson().fromJson(response.toString(), LoginResponse::class.java)

                if (loginResponse.status == 0) {
                    loginView.showLoginSuccess()
                } else {
                    val message = loginResponse.message
                    loginView.showLoginFailure(message)
                }
            },
            { error ->
                loginView.showLoginFailure(error.toString())
            })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    override fun onNoAccountLinkClicked() {
        loginView.navigateToRegistrationFragment()
    }


}
