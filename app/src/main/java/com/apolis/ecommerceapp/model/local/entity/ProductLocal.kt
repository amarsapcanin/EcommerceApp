package com.apolis.ecommerceapp.model.local.entity

data class ProductLocal(
    val description: String,
    val price: String,
    val product_id: String,
    val product_image_url: String,
    val product_name: String,
    val quantity: Int = 1,
    var total_price: Double = 0.0
)
