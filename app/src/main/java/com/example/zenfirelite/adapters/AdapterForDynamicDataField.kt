package com.example.zenfirelite.adapters

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.FormFieldTypeListItem
import com.example.zenfirelite.fragments.FormDetailsDirections
import com.example.zenfirelite.utils.ZTUtils

class AdapterForDynamicDataField(
    private val items: List<FormFieldTypeListItem>,
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // ViewHolder classes for each type of view
    inner class EditTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: FormFieldTypeListItem.EditTextType) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.text = item.title

            val editText = itemView.findViewById<EditText>(R.id.value)
            editText.hint = item.title

            when (item.inputType) {
                "NUMBER" -> {
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                    editText.setText(item.value)
                }
                "DATE" -> {
                    editText.isClickable = false
                    editText.isCursorVisible = false
                    editText.isFocusable = false
                    editText.isFocusableInTouchMode = false
                    editText.setText(ZTUtils.convertIsoToCustomFormat(item.value))

                }
                else -> {
                    editText.inputType = InputType.TYPE_CLASS_TEXT
                    editText.setText(item.value)
                }
            }

            if(item.isTextArea){
                val layoutParams = editText.layoutParams
                layoutParams.height = 500
                editText.layoutParams = layoutParams
                editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                editText.gravity = Gravity.TOP or Gravity.START
            }

            editText.setOnClickListener{
                if(item.inputType == "DATE"){
                    ZTUtils.openCalenderPicker(editText,null,null,context)
                }
            }


            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // No action needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Update the entered text in your data model
                }

                override fun afterTextChanged(s: Editable?) {
                    // No action needed
                    item.value = s?.toString().toString()
                }
            })


        }
    }

    inner class DropDownViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FormFieldTypeListItem.DropDownList) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.text = item.title

            val spinner = itemView.findViewById<Spinner>(R.id.chooseOptionSpinner)
            spinner.onItemSelectedListener
            val options = item.options
            val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
                context,
                R.layout.spinner_item,
                options
            )
            ad.setDropDownViewResource(
                R.layout.status_spinner_dropdown_item
            )
            spinner.adapter = ad
        }
    }

    inner class EditTextNumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FormFieldTypeListItem.EditTextTypeNum) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.inputType = InputType.TYPE_CLASS_NUMBER
            textView.text = item.title
        }
    }

    inner class RadioButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FormFieldTypeListItem.RadioButton) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            val recyclerView = itemView.findViewById<RecyclerView>(R.id.singleOptionRecycleView)
            textView.text = item.title
            val isRadioButton = item.isRadioButton
            recyclerView.layoutManager = LinearLayoutManager(context)
            val itemList = item.options
            val adapter = AdapterForRadioButtonItem(itemList, isRadioMode = isRadioButton)
            recyclerView.adapter = adapter
        }
    }

    inner class RadioButtonTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val YES = itemView.findViewById<TextView>(R.id.yes)
        val NO = itemView.findViewById<TextView>(R.id.no)
        val NA = itemView.findViewById<TextView>(R.id.NA)
        val reasonLayout = itemView.findViewById<LinearLayout>(R.id.reasons_layout)
        val reasonDescription = itemView.findViewById<EditText>(R.id.reasonDescription)
        fun bind(item: FormFieldTypeListItem.RadioTypeButton) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.text = item.title

            val descriptionValue = item.description

            when(item.value){
                "YES" -> YES.isSelected = true
                "NO" -> {
                    NO.isSelected = true
                    reasonLayout.visibility = View.VISIBLE
                    reasonDescription.setText(descriptionValue)
                }
                "NA" -> NA.isSelected = true
            }


            val clickListener = View.OnClickListener { view ->
                resetTextViewBackgrounds()
                view.isSelected = true
            }

            YES.setOnClickListener(clickListener)
            NO.setOnClickListener { view ->
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

    inner class SignaturePadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FormFieldTypeListItem.SignaturePadType) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            val addSignature = itemView.findViewById<TextView>(R.id.addSignature)
            textView.text = item.title

            addSignature.setOnClickListener {
                val action = FormDetailsDirections.actionFormDetailsToSignaturePad()
                val navController = Navigation.findNavController(itemView)
                navController.navigate(action)
            }


        }
    }

    inner class TableTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FormFieldTypeListItem.TableView) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.title)
            textView.text = item.title

            val editTable = itemView.findViewById<TextView>(R.id.editTable)
            editTable.setOnClickListener {
                val action = FormDetailsDirections.actionFormDetailsToTableInfo()
                val navController = Navigation.findNavController(itemView)
                navController.navigate(action)

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FormFieldTypeListItem.EditTextType -> 0
            is FormFieldTypeListItem.EditTextTypeNum -> 1
            is FormFieldTypeListItem.DropDownList -> 2
            is FormFieldTypeListItem.RadioButton -> 3
            is FormFieldTypeListItem.RadioTypeButton -> 4
            is FormFieldTypeListItem.SignaturePadType -> 5
            is FormFieldTypeListItem.TableView -> 6
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

            5 -> SignaturePadViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.filedtype_signature, parent, false)
            )

            6 -> TableTypeViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.filedtype_table, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is FormFieldTypeListItem.EditTextType -> (holder as EditTextViewHolder).bind(item)
            is FormFieldTypeListItem.EditTextTypeNum -> (holder as EditTextNumViewHolder).bind(item)
            is FormFieldTypeListItem.DropDownList -> (holder as DropDownViewHolder).bind(item)
            is FormFieldTypeListItem.RadioButton -> (holder as RadioButtonViewHolder).bind(item)
            is FormFieldTypeListItem.RadioTypeButton -> (holder as RadioButtonTypeViewHolder).bind(
                item
            )

            is FormFieldTypeListItem.SignaturePadType -> (holder as SignaturePadViewHolder).bind(
                item
            )

            is FormFieldTypeListItem.TableView -> (holder as TableTypeViewHolder).bind(item)
        }
    }

}