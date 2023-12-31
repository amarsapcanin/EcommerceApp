package com.apolis.ecommerceapp.model.remote.dto

data class OrderResponse(
    val message: String,
    val orders: List<Order>,
    val status: Int
)