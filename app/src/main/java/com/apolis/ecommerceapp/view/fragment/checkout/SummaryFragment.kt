package com.apolis.ecommerceapp.view.fragment.checkout

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apolis.ecommerceapp.databinding.FragmentSummaryBinding
import com.apolis.ecommerceapp.model.local.DbHandler
import com.apolis.ecommerceapp.model.local.dao.InfoDao
import com.apolis.ecommerceapp.model.local.dao.ProductDao
import com.apolis.ecommerceapp.model.preferences.SharedPreference
import com.apolis.ecommerceapp.model.remote.dto.OrderResponseSent
import com.apolis.ecommerceapp.view.activity.MainActivity
import com.apolis.ecommerceapp.view.adapter.CheckoutItemAdapter
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private lateinit var sharedPreference : SharedPreference
    private lateinit var dbHandler: DbHandler
    private lateinit var productDao: ProductDao
    private lateinit var infoDao: InfoDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initDao()
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val products = productDao.getAllProducts()

        val adapter = CheckoutItemAdapter(products)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            rcProducts.adapter = adapter
            rcProducts.layoutManager = layoutManager

            txtDeliveryTitle.text = infoDao.getLastInfo()?.addressTitle
            txtDeliveryAddress.text = infoDao.getLastInfo()?.address

            txtPaymentHolder.text = infoDao.getLastInfo()?.payment
            txtTotalBillHolder.text = "$ ${productDao.calculateTotalPriceInCart()}"

            btnConfirmOrder.setOnClickListener {
                newOrder()
                productDao.clearAllProducts()
                infoDao.clearAllInfo()
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.apply {
            txtDeliveryTitle.text = infoDao.getLastInfo()?.addressTitle
            txtDeliveryAddress.text = infoDao.getLastInfo()?.address

            txtPaymentHolder.text = infoDao.getLastInfo()?.payment
            txtTotalBillHolder.text = "$ ${productDao.calculateTotalPriceInCart()}"
        }

    }

    fun newOrder() {

        sharedPreference = SharedPreference(requireContext())
        val userId = sharedPreference.getId("userId")

        val jsonObject = JSONObject()
        jsonObject.put("user_id", userId)

        val deliveryAddress = infoDao.getLastInfo()

        if (deliveryAddress != null) {
            val deliveryAddressObject = JSONObject()
            deliveryAddressObject.put("title", deliveryAddress.addressTitle)
            deliveryAddressObject.put("address", deliveryAddress.address)
            jsonObject.put("delivery_address", deliveryAddressObject)
        }

        jsonObject.put("payment_method", "COD")

        val products = productDao.getAllProducts()

        val itemsArray = JSONArray()
        for (product in products) {
            val itemObject = JSONObject()
            itemObject.put("product_id", product.product_id)
            itemObject.put("quantity", product.quantity)
            itemObject.put("unit_price", product.price)
            itemsArray.put(itemObject)
        }
        jsonObject.put("items", itemsArray)
        jsonObject.put("bill_amount", productDao.calculateTotalPriceInCart())

        val request = JsonObjectRequest(
            Request.Method.POST, URL_NEW_ORDER, jsonObject,
            { response ->
                val orderResponseSent = Gson().fromJson(response.toString(), OrderResponseSent::class.java)
                if (orderResponseSent.status == 0) {
                    Toast.makeText(requireContext(), "ORDER PLACED!", Toast.LENGTH_SHORT).show()
                } else {
                    val message = orderResponseSent.message
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                // Handle error
            })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    private fun initDao() {
        dbHandler = DbHandler(requireContext())
        productDao = ProductDao(dbHandler)
        infoDao = InfoDao(dbHandler)
    }

    companion object {
        const val URL_NEW_ORDER = "http://10.0.2.2/myshop/index.php/Order"
    }
}