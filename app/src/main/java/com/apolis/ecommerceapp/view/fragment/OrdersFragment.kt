package com.apolis.ecommerceapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.apolis.ecommerceapp.databinding.FragmentOrdersBinding
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.remote.dto.OrderResponse
import com.apolis.ecommerceapp.presenter.OrderContract
import com.apolis.ecommerceapp.presenter.OrderPresenter
import com.apolis.ecommerceapp.view.adapter.OrdersAdapter

class OrdersFragment : Fragment(), OrderContract.OrderView {

    private lateinit var binding: FragmentOrdersBinding
    private lateinit var presenter: OrderContract.OrderPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = OrderPresenter(VolleyHandler(requireContext()), this)
        presenter.getOrders()
    }

    override fun orderSuccess(orderResponse: OrderResponse) {
        val adapter = OrdersAdapter(orderResponse.orders)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            rcOrders.adapter = adapter
            rcOrders.layoutManager = layoutManager
        }
    }

    override fun orderError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}