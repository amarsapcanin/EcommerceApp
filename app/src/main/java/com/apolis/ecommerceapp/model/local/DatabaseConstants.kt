package com.apolis.ecommerceapp.model.local

object DatabaseConstants {

    const val DATABASE_NAME = "ProductsInCart"
    const val DATABASE_VERSION = 1
    const val TABLE_PRODUCTS = "products"
    const val TABLE_INFO = "info"


    val CREATE_TABLE_PRODUCTS = """ CREATE TABLE $TABLE_PRODUCTS(
        product_id TEXT PRIMARY KEY,
        price TEXT,
        description TEXT,
        product_image_url TEXT,
        product_name TEXT,
        quantity INTEGER,
        total_price DOUBLE)
    """.trimIndent()

    val CREATE_TABLE_INFO = """ CREATE TABLE $TABLE_INFO(
        infoId INTEGER PRIMARY KEY,
        addressTitle TEXT,
        address TEXT,
        payment TEXT)
    """.trimIndent()
}