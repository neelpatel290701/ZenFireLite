package com.example.zenfirelite.adapters

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.FieldTypeListItem

class AdapterForDynamicDataField(private val items: List<FieldTypeListItem>,
                                 private val context : Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // ViewHolder classes for each type of view
    inner class EditTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: FieldTypeListItem.EditTextType) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.text = item.title

            val editText = itemView.findViewById<TextView>(R.id.value)
            if(item.inputType == "number") {
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }else{
                editText.inputType = InputType.TYPE_CLASS_TEXT
            }
        }
    }

    inner class DropDownViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: FieldTypeListItem.DropDownList) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.text = item.title

            val spinner = itemView.findViewById<Spinner>(R.id.chooseOptionSpinner)
            spinner.onItemSelectedListener
            val options = item.options
            val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
                context,
                R.layout.spinner_item,
                options)

            ad.setDropDownViewResource(
                R.layout.status_spinner_dropdown_item)

            spinner.adapter = ad
        }
    }

    inner class EditTextNumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FieldTypeListItem.EditTextTypeNum) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.inputType = InputType.TYPE_CLASS_NUMBER
            textView.text = item.title
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FieldTypeListItem.EditTextType-> 0
            is FieldTypeListItem.EditTextTypeNum -> 1
            is FieldTypeListItem.DropDownList -> 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> EditTextViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fieldtype_edittext, parent, false)
            )
            1 -> EditTextNumViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.sample_textfiledtype2, parent, false)
            )
            2 -> DropDownViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fieldtype_dropdown, parent, false)
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
            is FieldTypeListItem.DropDownList -> (holder as DropDownViewHolder).bind(item)
        }
    }

}