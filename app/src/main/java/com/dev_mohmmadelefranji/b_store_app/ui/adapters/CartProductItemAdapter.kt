package com.dev_mohmmadelefranji.b_store_app.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev_mohmmadelefranji.b_store_app.databinding.CartProductItemBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.Cart

class CartProductItemAdapter(private val onValueOfQuantityChangeListener: OnValueOfQuantityChangeListener) :
    ListAdapter<Cart, CartProductItemAdapter.MainViewHolder>(ProductDiffUtil()) {


    inner class MainViewHolder(val binding: CartProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Cart) {
            binding.item = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            CartProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val product = currentList[position]

        holder.bind(product = product)

        holder.binding.btnQuantity.number = currentList[position].quantityOfOrder.toString()


        holder.binding.btnQuantity.setOnValueChangeListener { view, oldValue, newValue ->
            onValueOfQuantityChangeListener.getNewValueOfQuantity(
                newValue = newValue,
                currentList[position].id
            )
        }

        Glide.with(holder.binding.root.context)
            .load(currentList[position].imageUrl)
            .circleCrop()
            .into(holder.binding.imageProduct)
    }

    interface OnValueOfQuantityChangeListener {
        fun getNewValueOfQuantity(newValue: Int, productID: Int)
    }

    class ProductDiffUtil : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(
            oldItem: Cart,
            newItem: Cart
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Cart,
            newItem: Cart
        ): Boolean {
            return (oldItem.id == newItem.id && oldItem.quantityOfOrder == newItem.quantityOfOrder)
        }
    }
}