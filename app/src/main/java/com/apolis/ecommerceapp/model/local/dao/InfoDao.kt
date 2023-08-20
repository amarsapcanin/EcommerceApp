package com.apolis.ecommerceapp.model.local.dao

import android.content.ContentValues
import android.database.Cursor
import com.apolis.ecommerceapp.model.local.DatabaseConstants.TABLE_INFO
import com.apolis.ecommerceapp.model.local.DbHandler
import com.apolis.ecommerceapp.model.local.entity.InfoLocal

class InfoDao(private val dbHandler: DbHandler) {


    fun addAddress(infoLocal: InfoLocal): Long {
        val contentValue = ContentValues()

        contentValue.apply {
            put("addressTitle", infoLocal.addressTitle)
            put("address", infoLocal.address)
            put("payment", infoLocal.payment)
        }

        return dbHandler.writableDatabase.insert(TABLE_INFO, null, contentValue)
    }

    fun updatePayment(newPayment: String) : Int {
        val contentValue = ContentValues()

        contentValue.apply {
            put("payment", newPayment )
        }

        return dbHandler.writableDatabase.update(TABLE_INFO, contentValue, null, null)
    }

    fun getLastInfo(): InfoLocal? {
        val query = "SELECT * FROM $TABLE_INFO ORDER BY ROWID DESC LIMIT 1"
        val cursor: Cursor = dbHandler.readableDatabase.rawQuery(query, null)

        return if (cursor.moveToFirst()) {
            val addressTitleIndex = cursor.getColumnIndex("addressTitle")
            val addressIndex = cursor.getColumnIndex("address")
            val paymentIndex = cursor.getColumnIndex("payment")

            val addressTitle = cursor.getString(addressTitleIndex)
            val address = cursor.getString(addressIndex)
            val payment = cursor.getString(paymentIndex)

            InfoLocal(1, addressTitle, address, payment)
        } else {
            null
        }
    }

    fun clearAllInfo(): Int {
        return dbHandler.writableDatabase.delete(TABLE_INFO, null, null)
    }
}