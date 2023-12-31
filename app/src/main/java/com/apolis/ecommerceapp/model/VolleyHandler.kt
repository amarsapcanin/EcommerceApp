package com.apolis.ecommerceapp.model

import android.content.Context
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apolis.ecommerceapp.model.preferences.SharedPreference
import com.apolis.ecommerceapp.model.remote.dto.CategoryResponse
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse
import com.apolis.ecommerceapp.model.remote.dto.LogoutResponse
import com.apolis.ecommerceapp.model.remote.dto.OrderResponse
import com.google.gson.Gson
import org.json.JSONObject

class VolleyHandler(private val context: Context) {

    private lateinit var sharedPreference : SharedPreference
    fun login(email: String, password: String, responseLoginCallback: ResponseLoginCallback){
        val jsonObject = JSONObject()
        jsonObject.put(KEY_EMAIL, email)
        jsonObject.put(KEY_PASSWORD, password)

        val request = JsonObjectRequest(
            Request.Method.POST, URL_LOGIN, jsonObject,
            { response ->
                val loginResponse = Gson().fromJson(response.toString(), LoginResponse::class.java)

                if (loginResponse.status == 0) {
                    responseLoginCallback.successLogin(loginResponse)

                    sharedPreference = SharedPreference(context)
                    val userId = loginResponse.user?.user_id
                    val userName = loginResponse.user?.full_name
                    val userNumber = loginResponse.user?.mobile_no

                    sharedPreference.saveId("userId", userId.toString())
                    sharedPreference.saveName("userName", userName.toString())
                    sharedPreference.saveNumber("userNumber", userNumber.toString())

                } else {
                    val message = loginResponse.message
                    responseLoginCallback.failureLogin(message)
                }
            },
            { error ->
                responseLoginCallback.failureLogin(error.toString())
            })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    fun register(fullName: String, mobileNo: String, email: String, password: String,
                 responseLoginCallback: ResponseLoginCallback) {

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
                        responseLoginCallback.successLogin(registrationResponse)
                    } else {
                        val message = registrationResponse.message
                        responseLoginCallback.failureLogin(message)
                    }
                },
                { error ->
                    responseLoginCallback.failureLogin(error.toString())
                })

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)
    }

    fun getCategories(responseCategoryResponse: ResponseCategoryCallback){
        val request = JsonObjectRequest(
            GET, URL_CATEGORY, null,
            { response ->
                val categoryResponse = Gson().fromJson(response.toString(), CategoryResponse::class.java)

                if (categoryResponse.status == 0) {
                    responseCategoryResponse.successCategory(categoryResponse)
                } else {
                    val message = categoryResponse.message
                    responseCategoryResponse.failureCategory(message)
                }
            },
            { error ->
                responseCategoryResponse.failureCategory(error.toString())
            })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    fun logout(email: String, responseLogoutCallback: ResponseLogoutCallback) {
        val requestObject = JSONObject()
        requestObject.put("email_id", email)

        val request = JsonObjectRequest(
            Request.Method.POST, URL_LOGOUT, requestObject,
            { response ->
                val logoutResponse = Gson().fromJson(response.toString(),
                    LogoutResponse::class.java)
                if (logoutResponse.status == 0) {
                    responseLogoutCallback.successLogout(logoutResponse)
                } else {
                    val message = logoutResponse.message
                    responseLogoutCallback.failureLogout(message)
                }
            },
            { error ->
                responseLogoutCallback.failureLogout(error.toString())
            }
        )

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    fun getOrders(responseOrderCallback: ResponseOrderCallback){
        sharedPreference = SharedPreference(context)
        val userId = sharedPreference.getId("userId")
        val url = URL_ORDERS + userId
        val request = JsonObjectRequest(
            GET, url, null,
            { response ->
                val ordersResponse = Gson().fromJson(response.toString(), OrderResponse::class.java)

                if (ordersResponse.status == 0) {
                    responseOrderCallback.successOrder(ordersResponse)
                } else {
                    val message = ordersResponse.message
                    responseOrderCallback.failureOrder(message)
                }
            },
            { error ->
                responseOrderCallback.failureOrder(error.toString())
            })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }


    companion object {
        const val URL_LOGIN = "http://10.0.2.2/myshop/index.php/User/auth"
        const val URL_REG = "http://10.0.2.2/myshop/index.php/User/register"
        const val URL_CATEGORY = "http://10.0.2.2/myshop/index.php/Category"
        const val URL_LOGOUT = "http://10.0.2.2/myshop/index.php/User/logout"
        const val URL_ORDERS = "http://10.0.2.2/myshop/index.php/Order/userOrders/"
        const val KEY_EMAIL = "email_id"
        const val KEY_PASSWORD = "password"
        const val KEY_FULLNAME = "full_name"
        const val KEY_MOBILENO = "mobile_no"
    }
}