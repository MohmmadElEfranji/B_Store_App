package com.dev_mohmmadelefranji.b_store_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev_mohmmadelefranji.b_store_app.databinding.SpinnerBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.Address
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.PowerSpinnerInterface
import com.skydoves.powerspinner.PowerSpinnerView

class MySpinnerAdapter(
    powerSpinnerView: PowerSpinnerView,
) : ListAdapter<Address, MySpinnerAdapter.MySpinnerViewHolder>(SpinnerDiffUtil()),
    PowerSpinnerInterface<Address> {

    override var index: Int = powerSpinnerView.selectedIndex
    override val spinnerView: PowerSpinnerView = powerSpinnerView
    override var onSpinnerItemSelectedListener: OnSpinnerItemSelectedListener<Address>? =
        null


    inner class MySpinnerViewHolder(val binding: SpinnerBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySpinnerViewHolder {
        val binding =
            SpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MySpinnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MySpinnerViewHolder, position: Int) {

        holder.binding.tvNameS.text = currentList[position].name
        holder.binding.tvNameS.setOnClickListener {
            notifyItemSelected(position)
        }
    }

    // You must call the `spinnerView.notifyItemSelected` method to let `PowerSpinnerView` know the item is changed.
/*    override fun notifyItemSelected(index: Int) {
        val oldIndex = this.index
        this.index = index
        this.spinnerView.notifyItemSelected(index, this.currentList[index].name)
        this.onSpinnerItemSelectedListener?.onItemSelected(
            oldIndex = oldIndex,
            oldItem = oldIndex.takeIf { it != -1 }?.let { currentList[oldIndex] },
            newIndex = index,
            newItem = item
        )
    }*/


    class SpinnerDiffUtil : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(
            oldItem: Address,
            newItem: Address
        ): Boolean {
            return oldItem.addressID == newItem.addressID
        }

        override fun areContentsTheSame(
            oldItem: Address,
            newItem: Address
        ): Boolean {
            return (oldItem.name == newItem.name && oldItem.addressID == newItem.addressID)
        }
    }


    // You must call the `spinnerView.notifyItemSelected` method to let `PowerSpinnerView` know the item is changed.
    override fun notifyItemSelected(index: Int) {
        if (index == -1) return
        val oldIndex = this.index
        this.index = index
        this.spinnerView.notifyItemSelected(index, this.currentList[index].name)
        this.onSpinnerItemSelectedListener?.onItemSelected(
            oldIndex = oldIndex,
            oldItem = oldIndex.takeIf { it != -1 }?.let { currentList[oldIndex] },
            newIndex = index,
            newItem = currentList[index]
        )
    }

    override fun setItems(itemList: List<Address>) {}

}