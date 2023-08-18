package com.apolis.ecommerceapp.view.fragment.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.FragmentPaymentBinding
import com.apolis.ecommerceapp.view.adapter.PaymentAdapter

class PaymentFragment : Fragment(), PaymentAdapter.ItemClickPaymentListener {

    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paymentList = listOf(
            "Cash On Delivery",
            "Internet Banking",
            "Debit Card / Credit Card",
            "Pay Pal")

        val adapter = PaymentAdapter(paymentList, this)
        val layoutManager = LinearLayoutManager(requireContext())

        binding.apply {
            rcPayment.adapter = adapter
            rcPayment.layoutManager = layoutManager
        }

        binding.btnNext.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager2)
            val currentItem = viewPager.currentItem
            viewPager.setCurrentItem(currentItem + 1, true)
        }
    }

    override fun onItemClick(payment: String) {

    }
}