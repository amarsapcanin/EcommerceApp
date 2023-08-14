package com.apolis.ecommerceapp.model.remote.dto

data class ProductDetails(
    val average_rating: String,
    val category_id: String,
    val description: String,
    val images: List<Image>,
    val is_active: String,
    val price: String,
    val product_id: String,
    val product_image_url: String,
    val product_name: String,
    val reviews: List<Any>,
    val specifications: List<Specification>,
    val sub_category_id: String
)