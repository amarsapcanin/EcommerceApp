package com.apolis.ecommerceapp.model

import com.apolis.ecommerceapp.model.remote.dto.CategoryResponse
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse

interface ResponseCallback {
    fun successLogin(loginResponse: LoginResponse)
    fun successCategory(categoryResponse: CategoryResponse)
    fun failure(error: String)
}