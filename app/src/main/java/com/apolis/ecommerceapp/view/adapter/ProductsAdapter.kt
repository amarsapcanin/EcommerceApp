package com.apolis.ecommerceapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.ProductItemBinding
import com.apolis.ecommerceapp.model.remote.dto.Product
import com.squareup.picasso.Picasso

class ProductsAdapter(private val products: MutableList<Product>,
                      private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    interface ItemClickListener {
        fun onProductClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun updateData(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
                val clickedProduct = products[adapterPosition]
                itemClickListener.onProductClick(clickedProduct)
            }
        }

        fun bind(product: Product){
            binding.apply {
                /*Picasso
                    .get()
                    .load(CategoryAdapter.URL_IMAGE +product.product_image_url)
                    .into(imgProduct)*/

                Picasso
                    .get()
                    .load(R.drawable.iphone)
                    .into(imgProduct)

                txtNameOfProduct.text = product.product_name
                txtDescriptionOfProduct.text = product.description
                txtPriceOfProduct.text = "$ ${product.price}"

            }
        }
    }

    companion object {
        const val URL_IMAGE = "http://10.0.2.2/myshop/images/"
    }
}
