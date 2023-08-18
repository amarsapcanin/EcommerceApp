package com.apolis.ecommerceapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.FragmentCheckoutBinding
import com.apolis.ecommerceapp.view.adapter.ViewpagerCheckoutAdapter
import com.apolis.ecommerceapp.view.fragment.checkout.CartItemsFragment
import com.apolis.ecommerceapp.view.fragment.checkout.DeliveryFragment
import com.apolis.ecommerceapp.view.fragment.checkout.PaymentFragment
import com.apolis.ecommerceapp.view.fragment.checkout.SummaryFragment
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf(CartItemsFragment(), DeliveryFragment(), PaymentFragment(),
            SummaryFragment())
        val adapter = ViewpagerCheckoutAdapter(fragments, requireActivity())

        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Cart Items"
                }
                1 -> {
                    tab.text = "Delivery"
                }
                2 -> {
                    tab.text = "Payment"
                }
                3 -> {
                    tab.text = "Summary"
                }
            }
        }.attach()
    }
}