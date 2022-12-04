package com.dev_mohmmadelefranji.b_store_app.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev_mohmmadelefranji.b_store_app.databinding.ProfileItemBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.ProfileItem

class ProfileItemAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<ProfileItem, ProfileItemAdapter.MainViewHolder>(ProductDiffUtil()) {


    inner class MainViewHolder(val binding: ProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val profileItem = currentList[position]

        holder.binding.icon.setImageResource(profileItem.imageUrl)
        holder.binding.title.text = profileItem.title

        holder.binding.arrowRight.setOnClickListener {
            onItemClickListener.onClickListener(position = position)
        }


    }

    interface OnItemClickListener {
        fun onClickListener(position: Int)
    }


    class ProductDiffUtil : DiffUtil.ItemCallback<ProfileItem>() {
        override fun areItemsTheSame(
            oldItem: ProfileItem,
            newItem: ProfileItem
        ): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(
            oldItem: ProfileItem,
            newItem: ProfileItem
        ): Boolean {
            return (oldItem.itemId == newItem.itemId && oldItem.title == newItem.title)
        }
    }
}