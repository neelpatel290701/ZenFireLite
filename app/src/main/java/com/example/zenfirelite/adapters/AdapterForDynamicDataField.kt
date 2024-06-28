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
import com.example.zenfirelite.databinding.FieldtypeDropdownBinding
import com.example.zenfirelite.databinding.FieldtypeEdittextBinding
import com.example.zenfirelite.databinding.FieldtypeRadiobuttonBinding
import com.example.zenfirelite.databinding.FieldtypeRadiotypebuttonBinding
import com.example.zenfirelite.databinding.FiledtypeSignatureBinding
import com.example.zenfirelite.databinding.FiledtypeTableBinding
import com.example.zenfirelite.databinding.SampleTextfiledtype2Binding
import com.example.zenfirelite.datamodels.FormFieldTypeListItem
import com.example.zenfirelite.fragments.FormDetailsDirections
import com.example.zenfirelite.utils.ZTUtils

class AdapterForDynamicDataField(
    val items: List<FormFieldTypeListItem>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // ViewHolder classes for each type of view
    inner class EditTextViewHolder(private val binding: FieldtypeEdittextBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: FormFieldTypeListItem.EditTextType) {
            binding.title.text = item.title
            binding.value.hint = item.title

            when (item.inputType) {
                "NUMBER" -> {
                    binding.value.inputType = InputType.TYPE_CLASS_NUMBER
                    binding.value.setText(item.value)
                }
                "DATE" -> {
                    binding.value.apply {
                        isClickable = false
                        isCursorVisible = false
                        isFocusable = false
                        isFocusableInTouchMode = false
                        setText(ZTUtils.convertIsoToCustomFormat(item.value))
                    }
                }
                else -> {
                    binding.value.inputType = InputType.TYPE_CLASS_TEXT
                    binding.value.setText(item.value)
                }
            }

            if (item.isTextArea) {
                binding.value.apply {
                    layoutParams.height = 500
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    gravity = Gravity.TOP or Gravity.START
                }
            }

            binding.value.setOnClickListener {
                if (item.inputType == "DATE") {
                    ZTUtils.openCalenderPicker(binding.value, null, null, context){
                        selectedDate ->
                        binding.value.setText(selectedDate)
                    }

                }
            }

            binding.value.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    item.value = s?.toString().toString()

                    if(item.inputType == "DATE"){
                        item.value = ZTUtils.convertDateToIso(item.value)
                    }
                }
            })
        }
    }

    inner class DropDownViewHolder(private val binding: FieldtypeDropdownBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormFieldTypeListItem.DropDownList) {
            binding.title.text = item.title
            val adapter = ArrayAdapter(context, R.layout.spinner_item, item.options)
            adapter.setDropDownViewResource(R.layout.status_spinner_dropdown_item)
            binding.chooseOptionSpinner.adapter = adapter
        }
    }

    inner class RadioButtonViewHolder(private val binding: FieldtypeRadiobuttonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormFieldTypeListItem.RadioButton) {
            binding.title.text = item.title
            binding.singleOptionRecycleView.layoutManager = LinearLayoutManager(context)
            val adapter = AdapterForRadioButtonItem(item.options, item.isRadioButton)
            binding.singleOptionRecycleView.adapter = adapter
        }
    }

    inner class RadioButtonTypeViewHolder(private val binding: FieldtypeRadiotypebuttonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormFieldTypeListItem.RadioTypeButton) {
            binding.title.text = item.title
            resetTextViewBackgrounds()

            when (item.value) {
                "YES" -> binding.yes.isSelected = true
                "NO" -> {
                    binding.no.isSelected = true
                    binding.reasonsLayout.visibility = View.VISIBLE
                    binding.reasonDescription.setText(item.description)
                }
                "NA" -> binding.NA.isSelected = true
            }

            binding.yes.setOnClickListener {
                item.value = "YES"
                resetTextViewBackgrounds()
                binding.yes.isSelected = true
            }

            binding.no.setOnClickListener {
                item.value = "NO"
                resetTextViewBackgrounds()
                binding.no.isSelected = true
                binding.reasonsLayout.visibility = View.VISIBLE
                binding.reasonDescription.setText(item.description)
            }

            binding.NA.setOnClickListener {
                item.value = "NA"
                resetTextViewBackgrounds()
                binding.NA.isSelected = true
            }
        }

        private fun resetTextViewBackgrounds() {
            binding.yes.isSelected = false
            binding.no.isSelected = false
            binding.NA.isSelected = false
            binding.reasonsLayout.visibility = View.GONE
        }
    }

    inner class SignaturePadViewHolder(private val binding: FiledtypeSignatureBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormFieldTypeListItem.SignaturePadType) {
            binding.title.text = item.title
            binding.addSignature.setOnClickListener {
                val action = FormDetailsDirections.actionFormDetailsToSignaturePad()
                val navController = Navigation.findNavController(itemView)
                navController.navigate(action)
            }
        }
    }

    inner class TableTypeViewHolder(private val binding: FiledtypeTableBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormFieldTypeListItem.TableView) {
            binding.title.text = item.title
            binding.editTable.setOnClickListener {
                val action = FormDetailsDirections.actionFormDetailsToTableInfo()
                val navController = Navigation.findNavController(itemView)
                navController.navigate(action)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FormFieldTypeListItem.EditTextType -> 0
            is FormFieldTypeListItem.DropDownList -> 1
            is FormFieldTypeListItem.RadioButton -> 2
            is FormFieldTypeListItem.RadioTypeButton -> 3
            is FormFieldTypeListItem.SignaturePadType -> 4
            is FormFieldTypeListItem.TableView -> 5
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> EditTextViewHolder(FieldtypeEdittextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            1 -> DropDownViewHolder(FieldtypeDropdownBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            2 -> RadioButtonViewHolder(FieldtypeRadiobuttonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            3 -> RadioButtonTypeViewHolder(FieldtypeRadiotypebuttonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            4 -> SignaturePadViewHolder(FiledtypeSignatureBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            5 -> TableTypeViewHolder(FiledtypeTableBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
            is FormFieldTypeListItem.DropDownList -> (holder as DropDownViewHolder).bind(item)
            is FormFieldTypeListItem.RadioButton -> (holder as RadioButtonViewHolder).bind(item)
            is FormFieldTypeListItem.RadioTypeButton -> (holder as RadioButtonTypeViewHolder).bind(item)
            is FormFieldTypeListItem.SignaturePadType -> (holder as SignaturePadViewHolder).bind(item)
            is FormFieldTypeListItem.TableView -> (holder as TableTypeViewHolder).bind(item)
        }
    }
}
