package com.apolis.ecommerceapp.presenter

import com.apolis.ecommerceapp.model.ResponseOrderCallback
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.remote.dto.OrderResponse

class OrderPresenter(private val volleyHandler: VolleyHandler,
                     private val orderView: OrderContract.OrderView) :
    OrderContract.OrderPresenter {
    override fun getOrders() {
        volleyHandler.getOrders(object : ResponseOrderCallback{
            override fun successOrder(orderResponse: OrderResponse) {
                orderView.orderSuccess(orderResponse)
            }

            override fun failureOrder(error: String) {
                orderView.orderError(error)
            }

        })
    }
}