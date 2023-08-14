package com.apolis.ecommerceapp.model.remote.dto

import android.os.Parcel
import android.os.Parcelable

data class Category(
    val category_id: String?,
    val category_image_url: String?,
    val category_name: String?,
    val is_active: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category_id)
        parcel.writeString(category_image_url)
        parcel.writeString(category_name)
        parcel.writeString(is_active)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}