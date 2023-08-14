package com.apolis.ecommerceapp.model.remote.dto

data class SubCategoryResponse(
    val message: String,
    val status: Int,
    val subcategories: List<Subcategory>
)