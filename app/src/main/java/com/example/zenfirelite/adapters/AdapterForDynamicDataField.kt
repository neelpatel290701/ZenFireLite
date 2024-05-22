package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.FieldTypeListItem

class AdapterForDynamicDataField(private val items: List<FieldTypeListItem>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // ViewHolder classes for each type of view
    inner class EditTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FieldTypeListItem.EditTextType) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.text = item.title
        }
    }

    inner class EditTextNumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FieldTypeListItem.EditTextTypeNum) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.text = item.title
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FieldTypeListItem.EditTextType-> 0
            is FieldTypeListItem.EditTextTypeNum -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> EditTextViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.sample_textfieldtype_1, parent, false)
            )
            1 -> EditTextNumViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.sample_textfiled_type2, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is FieldTypeListItem.EditTextType -> (holder as EditTextViewHolder).bind(item)
            is FieldTypeListItem.EditTextTypeNum -> (holder as EditTextNumViewHolder).bind(item)
        }
    }

}