package com.apolis.ecommerceapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apolis.ecommerceapp.databinding.PaymentItemBinding

class PaymentAdapter(private var payments: List<String>,
                     private val itemClickPaymentListener: ItemClickPaymentListener) :
    RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    interface ItemClickPaymentListener {
        fun onItemClick(payments: String)
    }

    var selectedPaymentPosition: Int = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentAdapter.PaymentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PaymentItemBinding.inflate(layoutInflater, parent, false)
        return PaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentAdapter.PaymentViewHolder, position: Int) {
        holder.bind(payments[position])
    }

    override fun getItemCount(): Int = payments.size

    inner class PaymentViewHolder(private val binding: PaymentItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.selectedPayment.setOnClickListener(this)
        }
            fun bind(payments: String){
                binding.txtPayment.text = payments

                binding.selectedPayment.isChecked = adapterPosition == selectedPaymentPosition
            }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION && selectedPaymentPosition != position) {
                val previousSelectedPosition = selectedPaymentPosition
                selectedPaymentPosition = position
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(position)
                itemClickPaymentListener.onItemClick(payments[position])
            }
        }
    }
}