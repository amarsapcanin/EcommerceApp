package com.apolis.ecommerceapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apolis.ecommerceapp.databinding.OrderItemBinding
import com.apolis.ecommerceapp.model.remote.dto.Order

class OrdersAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersAdapter.OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(layoutInflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    inner class OrderViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(order: Order) {
                binding.apply {
                    txtId.text = "Order ID: ${order.order_id}"
                    txtAddress.text = "Address: ${order.address}"
                    txtAmount.text = "Total Amount: $ ${order.bill_amount}"
                    txtOrderDate.text = "Order Date: ${order.order_date}"
                    txtOrderStatus.text = "Order Status: ${order.order_status}"
                }
            }
    }

}