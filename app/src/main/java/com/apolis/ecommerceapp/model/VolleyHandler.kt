package com.apolis.ecommerceapp.model

import android.content.Context
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apolis.ecommerceapp.model.remote.dto.CategoryResponse
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse
import com.google.gson.Gson
import org.json.JSONObject

class VolleyHandler(private val context: Context) {

    fun login(email: String, password: String, responseCallback: ResponseCallback){
        val jsonObject = JSONObject()
        jsonObject.put(KEY_EMAIL, email)
        jsonObject.put(KEY_PASSWORD, password)

        val request = JsonObjectRequest(
            Request.Method.POST, URL_LOGIN, jsonObject,
            { response ->
                val loginResponse = Gson().fromJson(response.toString(), LoginResponse::class.java)

                if (loginResponse.status == 0) {
                    responseCallback.successLogin(loginResponse)
                } else {
                    val message = loginResponse.message
                    responseCallback.failure(message)
                }
            },
            { error ->
                responseCallback.failure(error.toString())
            })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    fun register(fullName: String, mobileNo: String, email: String, password: String,
                 responseCallback: ResponseCallback) {

            val jsonObject = JSONObject()
            jsonObject.put(KEY_FULLNAME, fullName)
            jsonObject.put(KEY_MOBILENO, mobileNo)
            jsonObject.put(KEY_EMAIL, email)
            jsonObject.put(KEY_PASSWORD, password)

            val request = JsonObjectRequest(
                Request.Method.POST, URL_REG, jsonObject,
                { response ->
                    val registrationResponse = Gson().fromJson(response.toString(),
                        LoginResponse::class.java)

                    if (registrationResponse.status == 0) {
                        responseCallback.successLogin(registrationResponse)
                    } else {
                        val message = registrationResponse.message
                        responseCallback.failure(message)
                    }
                },
                { error ->
                    responseCallback.failure(error.toString())
                })

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)
    }

    fun getCategories(responseCallback: ResponseCallback){
        val request = JsonObjectRequest(
            GET, URL_CATEGORY, null,
            { response ->
                val categoriesResponse = Gson().fromJson(response.toString(), CategoryResponse::class.java)

                if (categoriesResponse.status == 0) {
                    responseCallback.successCategory(categoriesResponse)
                } else {
                    val message = categoriesResponse.message
                    responseCallback.failure(message)
                }
            },
            { error ->
                responseCallback.failure(error.toString())
            })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    companion object {
        const val URL_LOGIN = "http://10.0.2.2/myshop/index.php/User/auth"
        const val URL_REG = "http://10.0.2.2/myshop/index.php/User/register"
        const val URL_CATEGORY = "http://10.0.2.2/myshop/index.php/Category"
        const val KEY_EMAIL = "email_id"
        const val KEY_PASSWORD = "password"
        const val KEY_FULLNAME = "full_name"
        const val KEY_MOBILENO = "mobile_no"
    }
}