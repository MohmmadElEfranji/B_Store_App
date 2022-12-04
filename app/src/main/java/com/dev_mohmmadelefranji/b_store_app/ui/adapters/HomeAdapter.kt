package com.dev_mohmmadelefranji.b_store_app.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_mohmmadelefranji.b_store_app.databinding.HeaderDesignBinding
import com.dev_mohmmadelefranji.b_store_app.databinding.ItemDesignBinding
import com.dev_mohmmadelefranji.b_store_app.databinding.LatestProductItemBinding
import com.dev_mohmmadelefranji.b_store_app.databinding.ProductItemBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.*
import com.dev_mohmmadelefranji.b_store_app.utils.Constants.TYPE_CATEGORY_HEADER
import com.dev_mohmmadelefranji.b_store_app.utils.Constants.TYPE_CATEGORY_ITEM
import com.dev_mohmmadelefranji.b_store_app.utils.Constants.TYPE_FAMOUS_PRODUCTS_HEADER
import com.dev_mohmmadelefranji.b_store_app.utils.Constants.TYPE_Latest_PRODUCTS_HEADER
import com.dev_mohmmadelefranji.b_store_app.utils.Constants.TYPE_Latest_PRODUCT_ITEM
import com.dev_mohmmadelefranji.b_store_app.utils.Constants.TYPE_PRODUCT_ITEM

class HomeAdapter(private val context: Context, private var allCategory: List<Category>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    //region Items View Holders
    class CategoryItemViewHolder(val itemBinding: ItemDesignBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Category) {
            // itemBinding.item = item
        }
    }

    class ProductItemViewHolder(private val itemBinding: ProductItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Product) {
            itemBinding.item = item
        }
    }

    class LatestProductViewHolder(private val itemBinding: LatestProductItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: LatestProduct) {
            itemBinding.item = item
        }
    }
    //endregion

    //region Headers view holders
    class HeaderCategoryViewHolder(private val headerBinding: HeaderDesignBinding) :
        RecyclerView.ViewHolder(headerBinding.root) {
        fun bind(header: Headers.CategoryHeader) {
            headerBinding.header = header.name
        }
    }


    class HeaderLatestProductsViewHolder(private val headerBinding: HeaderDesignBinding) :
        RecyclerView.ViewHolder(headerBinding.root) {
        fun bind(header: Headers.LatestProductsHeader) {
            headerBinding.header = header.name
        }
    }

    class HeaderFamousProductsViewHolder(private val headerBinding: HeaderDesignBinding) :
        RecyclerView.ViewHolder(headerBinding.root) {
        fun bind(header: Headers.FamousProductsHeader) {
            headerBinding.header = header.name
        }
    }
    //endregion

    private val itemList = arrayListOf<Any>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_CATEGORY_ITEM -> CategoryItemViewHolder(
                ItemDesignBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            TYPE_PRODUCT_ITEM -> ProductItemViewHolder(
                ProductItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_Latest_PRODUCT_ITEM -> LatestProductViewHolder(
                LatestProductItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_CATEGORY_HEADER -> HeaderCategoryViewHolder(
                HeaderDesignBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_Latest_PRODUCTS_HEADER -> HeaderLatestProductsViewHolder(
                HeaderDesignBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            TYPE_FAMOUS_PRODUCTS_HEADER -> HeaderFamousProductsViewHolder(
                HeaderDesignBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when (holder) {

            is CategoryItemViewHolder -> {
                //    holder.bind(itemList[position] as DataOfMain)

                setCategoryItemRv(holder.itemBinding.rvCategories, allCategory)
            }

            is ProductItemViewHolder -> {
                holder.bind(itemList[position] as Product)

            }
            is LatestProductViewHolder -> {
                holder.bind(itemList[position] as LatestProduct)

            }
            is HeaderCategoryViewHolder -> {
                holder.bind(itemList[position] as Headers.CategoryHeader)

            }
            is HeaderLatestProductsViewHolder -> {
                holder.bind(itemList[position] as Headers.LatestProductsHeader)

            }
            is HeaderFamousProductsViewHolder -> {
                holder.bind(itemList[position] as Headers.FamousProductsHeader)

            }
        }


    }

    interface Item {
        fun getPosition(list: ArrayList<Any>, position: Int)
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        val list = itemList[position]

        return when (list) {
            is DataOfMain -> {
                TYPE_CATEGORY_ITEM
            }
            is Product -> {
                TYPE_PRODUCT_ITEM
            }
            is LatestProduct -> {
                TYPE_Latest_PRODUCT_ITEM
            }
            is Headers.CategoryHeader -> {
                TYPE_CATEGORY_HEADER
            }
            is Headers.LatestProductsHeader -> {
                TYPE_Latest_PRODUCTS_HEADER
            }
            is Headers.FamousProductsHeader -> {
                TYPE_FAMOUS_PRODUCTS_HEADER
            }
            else -> throw IllegalArgumentException("Invalid items")
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateList(updateList: List<Any>) {
        itemList.clear()
        itemList.addAll(updateList)
        notifyDataSetChanged()
    }


    private fun setCategoryItemRv(recyclerView: RecyclerView, categoryItem: List<Category>) {

        //val itemRvAdapter = CategoryItemAdapter(categoryItem)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        // recyclerView.adapter = itemRvAdapter
    }
}