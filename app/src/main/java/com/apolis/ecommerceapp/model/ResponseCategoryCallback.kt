package com.apolis.ecommerceapp.model

import com.apolis.ecommerceapp.model.remote.dto.CategoryResponse

interface ResponseCategoryCallback {

    fun successCategory(categoryResponse: CategoryResponse)

    fun failureCategory(error: String)
}