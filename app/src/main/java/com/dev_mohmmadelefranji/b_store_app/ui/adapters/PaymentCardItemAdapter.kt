package com.dev_mohmmadelefranji.b_store_app.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev_mohmmadelefranji.b_store_app.databinding.PaymentCardItemBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.PaymentCard
import com.vinaygaba.creditcardview.CardType

class PaymentCardItemAdapter :
    ListAdapter<PaymentCard, PaymentCardItemAdapter.MainViewHolder>(ProductDiffUtil()) {


    inner class MainViewHolder(val binding: PaymentCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: PaymentCard) {
            binding.card = card
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            PaymentCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val paymentCard = currentList[position]

        holder.bind(currentList[position])


        when (paymentCard.type) {
            "Visa" -> {
                holder.binding.paymentCard.type = CardType.VISA
            }
            "Master" -> {
                holder.binding.paymentCard.type = CardType.MASTERCARD
            }

        }

    }


    class ProductDiffUtil : DiffUtil.ItemCallback<PaymentCard>() {
        override fun areItemsTheSame(
            oldItem: PaymentCard,
            newItem: PaymentCard
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PaymentCard,
            newItem: PaymentCard
        ): Boolean {
            return (oldItem.id == newItem.id && oldItem.createdAt == newItem.createdAt)
        }
    }
}