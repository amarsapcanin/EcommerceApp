package com.apolis.ecommerceapp.model.remote.dto

data class AddressResponse(
    val addresses: List<Address>,
    val message: String,
    val status: Int
)