package com.dev_mohmmadelefranji.b_store_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev_mohmmadelefranji.b_store_app.databinding.CategoryItemBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.Category

class SubCategoryItemAdapter() :
    ListAdapter<Category, SubCategoryItemAdapter.MainViewHolder>(CategoryDiffUtil()) {


    inner class MainViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.tvCategoryName.text = currentList[position].nameEn

        Glide.with(holder.binding.root.context)
            .load(currentList[position].imageUrl)
            .circleCrop()
            .into(holder.binding.imageCategory)

    }

    class CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem.categoryID == newItem.categoryID
        }

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return (oldItem.categoryID == newItem.categoryID && oldItem.nameEn == newItem.nameEn)
        }
    }
}