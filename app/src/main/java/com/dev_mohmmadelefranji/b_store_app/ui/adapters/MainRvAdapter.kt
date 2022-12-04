package com.dev_mohmmadelefranji.b_store_app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev_mohmmadelefranji.b_store_app.databinding.MainRvRowItemBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.AllCategory
import com.dev_mohmmadelefranji.b_store_app.model.entity.AllProduct
import com.dev_mohmmadelefranji.b_store_app.model.entity.Category
import com.dev_mohmmadelefranji.b_store_app.model.entity.Product
import com.dev_mohmmadelefranji.b_store_app.model.entity.listener.OnClickItemListener
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.item_adapters.ProductItemAdapter

class MainRvAdapter(
    private var context: Context,
    private var allCategory: List<AllCategory>,
    private var allProducts: List<AllProduct>,
    private var onLikeProductListener: ProductItemAdapter.OnLikeProductListener,
    private var onClickItemListener: OnClickItemListener<Product>
) : ListAdapter<Product, MainRvAdapter.MainViewHolder>(ProductDiffUtil()) {

    fun addCategory(allCategory: List<AllCategory>) {
        this.allCategory = allCategory
    }

    fun addProduct(allProducts: List<AllProduct>) {
        this.allProducts = allProducts
    }

    inner class MainViewHolder(
        val binding: MainRvRowItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainRvRowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.rvCategoryTitle.text = allCategory[position].categoryTitle
        holder.binding.rvProductTitle.text = allProducts[position].productTitle

        setCategoryItemRv(holder.binding.rvCategories, allCategory[position].categoryItem)

        setProductItemRv(
            holder.binding.rvProducts,
            allProducts[position].productItem,
            onLikeProductListener,
            onClickItemListener
        )
    }

    override fun getItemCount(): Int {
        return allCategory.size
    }


    private fun setCategoryItemRv(recyclerView: RecyclerView, categoryItem: List<Category>) {

        //val itemRvAdapter = CategoryItemAdapter(categoryItem)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        // recyclerView.adapter = itemRvAdapter
    }

    private fun setProductItemRv(
        recyclerView: RecyclerView,
        productItem: List<Product>,
        onLikeProductListener: ProductItemAdapter.OnLikeProductListener,
        onClickItemListener: OnClickItemListener<Product>
    ) {

        val itemRvAdapter = ProductItemAdapter(
            onLikeProductListener,
            onClickItemListener
        )

        itemRvAdapter.submitList(productItem)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = itemRvAdapter
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
            return (oldItem.id == newItem.id && oldItem.infoAr == newItem.infoAr)
        }
    }
}