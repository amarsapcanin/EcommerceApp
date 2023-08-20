package com.apolis.ecommerceapp.model.local.dao

import android.content.ContentValues
import com.apolis.ecommerceapp.model.local.DatabaseConstants.TABLE_PRODUCTS
import com.apolis.ecommerceapp.model.local.DbHandler
import com.apolis.ecommerceapp.model.local.entity.ProductLocal

class ProductDao(private val dbHandler: DbHandler) {

    fun addProduct(productLocal: ProductLocal, quantity: Int) : Long {
        val contentValue = ContentValues()

        contentValue.apply {
            put("product_id", productLocal.product_id)
            put("price", productLocal.price)
            put("description", productLocal.description)
            put("product_image_url", productLocal.product_image_url)
            put("product_name", productLocal.product_name)
            put("quantity", quantity)
            put("total_price", productLocal.price.toDouble() * productLocal.quantity)
        }

        return dbHandler.writableDatabase.insert(TABLE_PRODUCTS, null, contentValue)
    }

    fun getAllProducts(): ArrayList<ProductLocal> {
        val productList = ArrayList<ProductLocal>()

        val cursor = dbHandler.readableDatabase.query(TABLE_PRODUCTS, null, null,
            null, null, null, null, null)

        while (cursor.moveToNext()){
            val id = cursor.getString(cursor.getColumnIndexOrThrow("product_id"))
            val price = cursor.getString(cursor.getColumnIndexOrThrow("price"))
            val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
            val productImage = cursor.getString(cursor.getColumnIndexOrThrow("product_image_url"))
            val productName = cursor.getString(cursor.getColumnIndexOrThrow("product_name"))
            val quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))
            val totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"))


            val product = ProductLocal(
                product_id = id,
                price = price,
                description = description,
                product_image_url = productImage,
                product_name = productName,
                quantity = quantity,
                total_price = totalPrice)

            productList.add(product)
        }
        cursor.close()
        return productList
    }

    fun updateQuantity(productId: String, newQuantity: Int) : Int {
        val contentValue = ContentValues()

        contentValue.apply {
            put("quantity", newQuantity)
            put("total_price", getProductPrice(productId) * newQuantity)
        }

        val selection = "product_id = ?"
        val selectionArgs = arrayOf(productId)

        return dbHandler.writableDatabase.update(TABLE_PRODUCTS, contentValue, selection, selectionArgs)
    }

    private fun getProductPrice(productId: String): Double {
        val cursor = dbHandler.readableDatabase.query(
            TABLE_PRODUCTS, arrayOf("price"), "product_id = ?",
            arrayOf(productId), null, null, null
        )
        cursor.moveToFirst()
        val price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
        cursor.close()
        return price
    }

    fun deleteProductById(productId: String): Int {
        val selection = "product_id = ?"
        val selectionArgs = arrayOf(productId)

        return dbHandler.writableDatabase.delete(TABLE_PRODUCTS, selection, selectionArgs)
    }

    fun getProductQuantity(productId: String): Int {
        val cursor = dbHandler.readableDatabase.query(
            TABLE_PRODUCTS, arrayOf("quantity"), "product_id = ?",
            arrayOf(productId), null, null, null
        )

        var quantity = 0

        if (cursor.moveToFirst()) {
            quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))
        }

        cursor.close()

        return quantity
    }


    fun calculateTotalPriceInCart(): Double {
        val cursor = dbHandler.readableDatabase.query(
            TABLE_PRODUCTS, arrayOf("price", "quantity"), "quantity > 0",
            null, null, null, null
        )

        var totalPrice = 0.0

        while (cursor.moveToNext()) {
            val productPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
            val productQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))
            val productTotalPrice = productPrice * productQuantity
            totalPrice += productTotalPrice
        }

        cursor.close()

        return totalPrice
    }

    fun clearAllProducts(): Int {
        return dbHandler.writableDatabase.delete(TABLE_PRODUCTS, null, null)
    }


}