package com.dev_mohmmadelefranji.b_store_app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.model.entity.TestSpinnerData

class SpinnerAdapter(context: Context, list: List<TestSpinnerData>) :
    ArrayAdapter<TestSpinnerData>(context, 0, list) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }


    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val test = getItem(position)

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.spinner_item, parent, false)

        test?.let {
            val text = view.findViewById<TextView>(R.id.tv_name_spinner)

            text.text = it.name

        }
        return view


    }
}

