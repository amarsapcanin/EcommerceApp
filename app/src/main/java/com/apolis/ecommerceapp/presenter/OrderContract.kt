package com.apolis.ecommerceapp.presenter

import com.apolis.ecommerceapp.model.remote.dto.OrderResponse

interface OrderContract {

    interface OrderView {

        fun orderSuccess(orderResponse: OrderResponse)

        fun orderError(message: String)
    }

    interface OrderPresenter {
        fun getOrders()
    }
}