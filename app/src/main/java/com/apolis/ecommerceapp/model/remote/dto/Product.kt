package com.apolis.ecommerceapp.model.remote.dto

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val average_rating: String?,
    val category_id: String?,
    val category_name: String?,
    val description: String?,
    val price: String?,
    val product_id: String?,
    val product_image_url: String?,
    val product_name: String?,
    val sub_category_id: String?,
    val subcategory_name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(average_rating)
        parcel.writeString(category_id)
        parcel.writeString(category_name)
        parcel.writeString(description)
        parcel.writeString(price)
        parcel.writeString(product_id)
        parcel.writeString(product_image_url)
        parcel.writeString(product_name)
        parcel.writeString(sub_category_id)
        parcel.writeString(subcategory_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}