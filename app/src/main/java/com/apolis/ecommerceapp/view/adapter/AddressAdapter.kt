package com.apolis.ecommerceapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apolis.ecommerceapp.databinding.AddressItemBinding
import com.apolis.ecommerceapp.model.remote.dto.Address

class AddressAdapter(private val addresses: List<Address>,
                     private val itemClickRadioListener: ItemClickRadioListener) :
    RecyclerView.Adapter<AddressAdapter.AddressesViewHolder>(){

    interface ItemClickRadioListener {
        fun onItemClick(address: Address)
    }

    var selectedAddressPosition: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressAdapter.AddressesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AddressItemBinding.inflate(layoutInflater, parent, false)
        return AddressesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressAdapter.AddressesViewHolder, position: Int) {
        holder.bind(addresses[position])
    }

    override fun getItemCount(): Int = addresses.size

    inner class AddressesViewHolder(private val binding: AddressItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            binding.selectedAddress.setOnClickListener(this)
        }

            fun bind(address: Address){
                binding.apply {
                    txtTitle.text = address.title
                    txtAddress.text = address.address

                    selectedAddress.isChecked = adapterPosition == selectedAddressPosition

                    selectedAddress.setOnClickListener {
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION && selectedAddressPosition != position) {
                            val previousSelectedPosition = selectedAddressPosition
                            selectedAddressPosition = position
                            notifyItemChanged(previousSelectedPosition)
                            notifyItemChanged(position)
                            itemClickRadioListener.onItemClick(addresses[position])
                        }
                    }
                }
            }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION && selectedAddressPosition != position) {
                val previousSelectedPosition = selectedAddressPosition
                selectedAddressPosition = position
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(position)
                itemClickRadioListener.onItemClick(addresses[position])
            }
        }
    }
}