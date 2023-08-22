package com.apolis.ecommerceapp.model

import com.apolis.ecommerceapp.model.remote.dto.OrderResponse

interface ResponseOrderCallback {
    fun successOrder(orderResponse: OrderResponse)

    fun failureOrder(error: String)
}