package com.apolis.ecommerceapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apolis.ecommerceapp.databinding.FragmentProductDetailsBinding
import com.apolis.ecommerceapp.model.local.DbHandler
import com.apolis.ecommerceapp.model.local.dao.ProductDao
import com.apolis.ecommerceapp.model.local.entity.ProductLocal
import com.apolis.ecommerceapp.model.remote.dto.ProductDetails
import com.apolis.ecommerceapp.model.remote.dto.ProductDetailsResponse
import com.apolis.ecommerceapp.view.adapter.CategoryAdapter
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var dbHandler: DbHandler
    private lateinit var productDao: ProductDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDao()

        val productId = arguments?.getString("productId")
        productId?.let { fetchProductDetails(it) }
    }

    private fun fetchProductDetails(productId: String) {
        val request = JsonObjectRequest(
            Request.Method.GET, URL_PRODUCT + productId, null,
            { response ->
                val productDetailsResponse = Gson().fromJson(response.toString(), ProductDetailsResponse::class.java)

                if (productDetailsResponse.status == 0) {
                    val product = productDetailsResponse.product
                    displayProduct(product)
                } else {
                    // Handle error here
                }
            },
            { error ->
                // Handle error here
            })
        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(request)
    }

    private fun displayProduct(product: ProductDetails) {
        binding.apply {
            txtTitle.text = product.product_name
            txtDescriptionOfProduct.text = product.description
            txtPriceOfProduct.text = "$ ${product.price}"

            Picasso.get()
                .load(CategoryAdapter.URL_IMAGE + product.product_image_url)
                .into(imgProduct)

            binding.apply {
                val productId = product.product_id

                var quantity = productDao.getProductQuantity(productId)

                if (quantity < 1) {
                    quantity = 1
                    binding.txtAddToCart.visibility = View.VISIBLE
                    binding.layoutAddToCart.visibility = View.GONE
                } else {
                    binding.txtAddToCart.visibility = View.GONE
                    binding.layoutAddToCart.visibility = View.VISIBLE
                    binding.txtQuantitiyCart.text = quantity.toString()
                }

                binding.txtAddToCart.setOnClickListener {
                    binding.txtAddToCart.visibility = View.GONE
                    binding.layoutAddToCart.visibility = View.VISIBLE


                    val productId = product.product_id.toString()
                    val productName = product.product_name.toString()
                    val productImage = product.product_image_url.toString()
                    val description = product.description.toString()
                    val price = product.price.toString()

                    val newProduct = ProductLocal(
                        product_id = productId,
                        product_name = productName,
                        price = price,
                        product_image_url = productImage,
                        description = description
                    )

                    productDao.addProduct(newProduct, quantity)
                }

                binding.btnMinus.setOnClickListener {
                    if (quantity > 1) {
                        quantity--
                        binding.txtQuantitiyCart.text = quantity.toString()
                        productDao.updateQuantity(productId, quantity)
                    } else {
                        binding.txtAddToCart.visibility = View.VISIBLE
                        binding.layoutAddToCart.visibility = View.GONE
                        productDao.deleteProductById(product.product_id.toString())
                    }
                }

                binding.btnPlus.setOnClickListener {
                    quantity++
                    binding.txtQuantitiyCart.text = quantity.toString()
                    productDao.updateQuantity(productId, quantity)
                }
            }
        }
    }

    private fun initDao() {
        dbHandler = DbHandler(requireContext())
        productDao = ProductDao(dbHandler)
    }

    companion object {
        const val URL_PRODUCT = "http://10.0.2.2/myshop/index.php/Product/details/"
    }

}
