package com.apolis.ecommerceapp.model.remote.dto

data class OrderResponseSent(
    val message: String,
    val order_id: Int,
    val status: Int
)