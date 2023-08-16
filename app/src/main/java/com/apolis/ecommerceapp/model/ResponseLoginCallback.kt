package com.apolis.ecommerceapp.model

import com.apolis.ecommerceapp.model.remote.dto.LoginResponse

interface ResponseLoginCallback {
    fun successLogin(loginResponse: LoginResponse)
    fun failureLogin(error: String)
}