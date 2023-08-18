package com.apolis.ecommerceapp.view.fragment.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.FragmentSummaryBinding
import com.apolis.ecommerceapp.model.local.DbHandler
import com.apolis.ecommerceapp.model.local.dao.ProductDao
import com.apolis.ecommerceapp.model.remote.dto.Address
import com.apolis.ecommerceapp.model.remote.dto.Category
import com.apolis.ecommerceapp.view.adapter.CheckoutItemAdapter

class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private lateinit var dbHandler: DbHandler
    private lateinit var productDao: ProductDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        }


        binding.apply {
            txtDeliveryTitle.text = "working on it"
            txtDeliveryAddress.text = "working on it"

            txtPaymentHolder.text = "working on it"
            txtTotalBillHolder.text = "$ ${productDao.calculateTotalPriceInCart()}"
        }

    }

    private fun initDao() {
        dbHandler = DbHandler(requireContext())
        productDao = ProductDao(dbHandler)
    }
}