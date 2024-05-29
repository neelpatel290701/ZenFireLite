package com.example.zenfirelite.adapters

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.FieldTypeListItem
import com.example.zenfirelite.datamodels.RadioButtonItem

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
    inner class RadioButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FieldTypeListItem.RadioButton) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            val recyclerView = itemView.findViewById<RecyclerView>(R.id.singleOptionRecycleView)
            textView.text = item.title
            val isRadioButton = item.isRadioButton
            recyclerView.layoutManager = LinearLayoutManager(context)
            val itemList = mutableListOf<RadioButtonItem>(
                RadioButtonItem("Item 1"),
                RadioButtonItem("Item 2"),
                RadioButtonItem("Item 3"),
                RadioButtonItem("Item 4"),
                RadioButtonItem("Item 5"),
            )
            val adapter = AdapterForRadioButtonItem(itemList,isRadioMode = isRadioButton)
            recyclerView.adapter = adapter
        }
    }

    inner class RadioButtonTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val YES = itemView.findViewById<TextView>(R.id.yes)
        val NO = itemView.findViewById<TextView>(R.id.no)
        val NA = itemView.findViewById<TextView>(R.id.NA)
        val reasonLayout = itemView.findViewById<LinearLayout>(R.id.reasons_layout)
        fun bind(item: FieldTypeListItem.RadioTypeButton) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.text = item.title
            val clickListener = View.OnClickListener { view ->
                resetTextViewBackgrounds()
                view.isSelected = true
            }

            YES.setOnClickListener(clickListener)
            NO.setOnClickListener{view->
                resetTextViewBackgrounds()
                view.isSelected = true
                reasonLayout.visibility = View.VISIBLE
            }
            NA.setOnClickListener(clickListener)

        }

        private fun resetTextViewBackgrounds() {
            YES.isSelected = false
            NO.isSelected = false
            NA.isSelected = false
            reasonLayout.visibility = View.GONE
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FieldTypeListItem.EditTextType-> 0
            is FieldTypeListItem.EditTextTypeNum -> 1
            is FieldTypeListItem.DropDownList -> 2
            is FieldTypeListItem.RadioButton -> 3
            is FieldTypeListItem.RadioTypeButton -> 4
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
            3 -> RadioButtonViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fieldtype_radiobutton, parent, false)
            )
            4 -> RadioButtonTypeViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fieldtype_radiotypebutton, parent, false)
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
            is FieldTypeListItem.RadioButton -> (holder as RadioButtonViewHolder).bind(item)
            is FieldTypeListItem.RadioTypeButton -> (holder as RadioButtonTypeViewHolder).bind(item)
        }
    }

}