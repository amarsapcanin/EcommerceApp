package com.apolis.ecommerceapp.model.remote.dto

data class ProductDetailsResponse(
    val message: String,
    val product: ProductDetails,
    val status: Int
)