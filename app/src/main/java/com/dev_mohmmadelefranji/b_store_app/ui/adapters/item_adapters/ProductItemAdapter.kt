package com.dev_mohmmadelefranji.b_store_app.ui.adapters.item_adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev_mohmmadelefranji.b_store_app.databinding.ProductItemBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.Product
import com.dev_mohmmadelefranji.b_store_app.model.entity.listener.OnClickItemListener
import com.like.LikeButton
import com.like.OnLikeListener

class ProductItemAdapter(
    private var onLikeProductListener: OnLikeProductListener,
    private var onClickItemListener: OnClickItemListener<Product>
) : ListAdapter<Product, ProductItemAdapter.MainViewHolder>(ProductDiffUtil()) {


    inner class MainViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.item = product


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val product = currentList[position]

        holder.bind(product = product)

        /* holder.binding.tvProductName.text = productItem[position].nameEn*/
        holder.binding.rbRatingBar.rating = currentList[position].productRate.toFloat()


        // holder.binding.tvProductPrice.text = "$  ${productItem[position].price}"

        holder.binding.btnFavorite.isLiked = currentList[position].isFavorite


        Glide.with(holder.binding.root.context)
            .load(currentList[position].imageUrl)
            .circleCrop()
            .into(holder.binding.imageProduct)

        holder.binding.btnFavorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                onLikeProductListener.likedListener(
                    currentList[holder.adapterPosition].id,
                    holder.adapterPosition
                )
            }

            override fun unLiked(likeButton: LikeButton?) {
                onLikeProductListener.likedListener(
                    currentList[holder.adapterPosition].id,
                    holder.adapterPosition
                )

            }
        })


        holder.binding.parentLayout.setOnClickListener {
            onClickItemListener.onClickItem(currentList[position])
        }

    }

    interface OnLikeProductListener {
        fun likedListener(productID: Int, position: Int)
    }


    class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return (oldItem.id == newItem.id && oldItem.isFavorite == newItem.isFavorite)
        }
    }
}