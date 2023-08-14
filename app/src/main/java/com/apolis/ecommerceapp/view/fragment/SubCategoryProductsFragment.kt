package com.apolis.ecommerceapp.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apolis.ecommerceapp.databinding.FragmentSubCategoryProductsBinding
import com.apolis.ecommerceapp.model.remote.dto.ProductResponse
import com.apolis.ecommerceapp.view.adapter.ProductsAdapter
import com.google.gson.Gson

class SubCategoryProductsFragment : Fragment() {

    private lateinit var binding: FragmentSubCategoryProductsBinding
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubCategoryProductsBinding.inflate(inflater, container, false)
        val subcategoryId = arguments?.getString("subcategoryId")

        productsAdapter = ProductsAdapter(mutableListOf())
        binding.productRecyclerView.adapter = productsAdapter
        binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        getProducts(subcategoryId)

        return binding.root
    }

    private fun getProducts(subcategoryId: String?) {
        val request = JsonObjectRequest(
            Request.Method.GET, URL+subcategoryId, null,
            { response ->
                val productResponse = Gson().fromJson(response.toString(), ProductResponse::class.java)

                if (productResponse.status == 0) {
                    val products = productResponse.products
                    Log.i("TAGGGGG", products.toString())
                    productsAdapter.updateData(products)
                } else {
                }
            },
            { error ->
                println("Error: ${error.message}")
            })
        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(request)
    }


    companion object {
        fun newInstance(subcategoryId: String): SubCategoryProductsFragment {
            val fragment = SubCategoryProductsFragment()
            val args = Bundle()
            args.putString("subcategoryId", subcategoryId)
            fragment.arguments = args
            return fragment
        }

        const val URL = "http://10.0.2.2/myshop/index.php/SubCategory/products/"
    }
}

