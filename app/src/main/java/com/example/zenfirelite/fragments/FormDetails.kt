package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForDynamicDataField
import com.example.zenfirelite.adapters.AdapterForFormSectionsList
import com.example.zenfirelite.databinding.FragmentFormDetailsBinding
import com.example.zenfirelite.datamodels.FormFieldTypeListItem
import com.example.zenfirelite.datamodels.RadioButtonItem
import com.example.zenfirelite.datamodels.SectionData
import com.example.zenfirelite.viewmodels.FormDetailsViewModel
import com.example.zenfirelite.viewmodels.FormTemplateDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


@Suppress("DEPRECATION")
class FormDetails : Fragment()  {

    companion object {
        const val SIGNATURE_RESULT_KEY = "signature_result_key"
        const val SIGNATURE_DATA_KEY = "signature_data_key"
    }

    private var currSectionIndex = 0

    private val formTemplateDetailsViewModel : FormTemplateDetailsViewModel by activityViewModels()
    private val previousFormDetailsviewModel: FormDetailsViewModel by activityViewModels()

    private lateinit var binding : FragmentFormDetailsBinding
    private lateinit var navController: NavController
    val args : FormDetailsArgs by navArgs()

    private var sectionArray: Array<SectionData> = emptyArray()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().title = ""
        currSectionIndex = args.sectionIndex
    }
    @SuppressLint("SetTextI18n", "InflateParams", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentFormDetailsBinding.inflate(inflater, container, false)

        if(args.isFromFormTemplate) {

            formTemplateDetailsViewModel.formTemplateDetails.observe(viewLifecycleOwner, Observer {formDetails->
                val sectionList: List<SectionData> = formDetails?.sections ?: emptyList()

                // Create a deep copy of sectionList
                val copiedSectionList = sectionList.deepCopy()
                sectionArray = copiedSectionList.toTypedArray()

               // sectionArray = sectionList.toTypedArray()
                Log.d("neel", "FormTemplate - Observer triggered, sectionArray size: ${sectionArray.size}")
                if (sectionArray.isNotEmpty()) {
                    val formName = formDetails.displayName
                    requireActivity().title = formName
                    updateUI()
                }
            })

        }else{
            previousFormDetailsviewModel.formDetails.observe(viewLifecycleOwner, Observer {formDetails->
                val sectionList: List<SectionData> = formDetails?.result?.sections ?: emptyList()
                sectionArray = sectionList.toTypedArray()
                Log.d("neel", "Previous Form - Observer triggered, sectionArray size: ${sectionArray.size}")
                if (sectionArray.isNotEmpty()) {
                    val formName = formDetails?.result?.displayName ?:""
                    requireActivity().title = formName
                    updateUI()
                }
            })

        }

        setHasOptionsMenu(true)

        return binding.root
    }

    fun List<SectionData>.deepCopy(): List<SectionData> {
        return this.map { section ->
            section.copy(
                fields = section.fields?.map { field ->
                    field.copy()
                }
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        if (sectionArray.isNotEmpty()) {
            updateUI()
        }

    }

    private fun updateUI() {

        binding.formSectionName.hint = sectionArray[args.sectionIndex].displayName

        binding.formSectionName.setOnClickListener {
            openBottomSheetDialogForSectionList()
        }

        binding.nextSection.setOnClickListener {
            saveCurrentSectionData()
            if (currSectionIndex + 1 in sectionArray.indices) {
                currSectionIndex++
                onChangeSectionIndexUpdateSectionItems(currSectionIndex)
            }
        }

        binding.peviousSection.setOnClickListener {
            saveCurrentSectionData()
            if (currSectionIndex - 1 in sectionArray.indices) {
                currSectionIndex--
                onChangeSectionIndexUpdateSectionItems(currSectionIndex)
            }
        }

        binding.dataFieldRecyclerView.layoutManager = LinearLayoutManager(context)
        onChangeSectionIndexUpdateSectionItems(args.sectionIndex)
    }


    override fun onResume() {
        super.onResume()

        onChangeSectionIndexUpdateSectionItems(currSectionIndex)

        parentFragmentManager.setFragmentResultListener(SIGNATURE_RESULT_KEY, this) { _, result ->
            val byteArray = result.getByteArray(SIGNATURE_DATA_KEY)
            byteArray?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
//                imageView.setImageBitmap(bitmap)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        parentFragmentManager.clearFragmentResultListener(SIGNATURE_RESULT_KEY)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbaritems, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem_save = menu.findItem(R.id.save_formDetails)
        menuItem_save.isVisible = true
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_formDetails -> {
                Toast.makeText(requireContext(), "Details Saved", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onChangeSectionIndexUpdateSectionItems(currSectionIndex : Int) {

        when(currSectionIndex) {
            0 -> { binding.peviousSection.setTextColor(Color.parseColor("#D3D3D3"))
                   binding.nextSection.setTextColor(Color.parseColor("#318CE7"))
                 }
            sectionArray.size-1 -> {
                  binding.nextSection.setTextColor(Color.parseColor("#D3D3D3"))
                  binding.peviousSection.setTextColor(Color.parseColor("#318CE7"))
                 }
            else -> {
                 binding.nextSection.setTextColor(Color.parseColor("#318CE7"))
                 binding.peviousSection.setTextColor(Color.parseColor("#318CE7"))
                 }
        }

        if(currSectionIndex in sectionArray.indices){

            val selectedSectionArray = sectionArray[currSectionIndex]

            val fieldTypeListItems: List<FormFieldTypeListItem> =
                selectedSectionArray.fields?.map { field ->
                    when (field.uiType) {
                        "AUTO_POPULATE" -> FormFieldTypeListItem.EditTextType(field.displayName, field.dataType, field.value?.toString() ?: "",false,)
                        "INPUT" -> FormFieldTypeListItem.EditTextType(field.displayName,field.dataType,field.value?.toString() ?: "",false)
                        "TEXTAREA"->FormFieldTypeListItem.EditTextType(field.displayName,field.dataType,field.value?.toString() ?: "",true)
                        "DROPDOWN" -> {
                            val dropdownOptions = (field.options)?.dropdownOptions ?: emptyList()
                            FormFieldTypeListItem.DropDownList(field.displayName, dropdownOptions.map { it.value ?: "" })
                        }
                        "CHECKBOX" -> {
                            val valueMap = (field.value as? Map<*, *>)
                                ?.filterKeys { it is String }
                                ?.filterValues { it is Boolean }
                                ?.mapKeys { it.key as String }
                                ?.mapValues { it.value as Boolean }
                                ?: emptyMap()

                            val checkboxOptions = (field.options)?.checkboxOptions ?: emptyList()
//                            val valueMap = field.value as? Map<String, Boolean> ?: emptyMap()
                            val radioButtonItems = checkboxOptions.map {
                                RadioButtonItem(it.value ?: "", valueMap[it.key] ?: false)
                            }
                            FormFieldTypeListItem.RadioButton(field.displayName, false, radioButtonItems)
                        }
                        "RADIO" -> {
                            val valueMap = (field.value as? Map<*, *>)
                                ?.filterKeys { it is String }
                                ?.filterValues { it is Boolean }
                                ?.mapKeys { it.key as String }
                                ?.mapValues { it.value as Boolean }
                                ?: emptyMap()
                            val radioOptions = (field.options)?.radioOptions ?: emptyList()
//                            val valueMap = field.value as? Map<String, Boolean> ?: emptyMap()
                            val radioButtonItems = radioOptions.map {
                                RadioButtonItem(it.value ?: "", valueMap[it.key] ?: false)
                            }
                            FormFieldTypeListItem.RadioButton(field.displayName, true, radioButtonItems)
                        }
                        "BUTTON_RADIO" -> {
                            val description = field.reasons?.firstOrNull()?.description ?: ""
                            FormFieldTypeListItem.RadioTypeButton(field.displayName,field.value.toString(),description)
                        }
                        "TABLE" -> FormFieldTypeListItem.TableView(field.displayName)
                        "SIGNATURE_PAD" -> FormFieldTypeListItem.SignaturePadType(field.displayName)
                        else -> FormFieldTypeListItem.EditTextType(field.displayName, "text",field.value?.toString() ?: "",false)
                    }
                } ?: emptyList()

            val adapter = context?.let { AdapterForDynamicDataField(fieldTypeListItems, it) }
            binding.dataFieldRecyclerView.adapter = adapter
            adapter!!.notifyDataSetChanged()
            binding.formSectionName.hint = selectedSectionArray.displayName

        }else{
            // Handle the case where the index is out of bounds
            Log.d("neel","Invalid Section Index, ")
        }

    }

    private fun saveCurrentSectionData() {
        val selectedSectionArray = sectionArray[currSectionIndex]

        // Retrieve updated items from the adapter
        val updatedItems =
            (binding.dataFieldRecyclerView.adapter as? AdapterForDynamicDataField)?.items ?: return

        // Update the fields in the selected section using the updated items directly by index
        selectedSectionArray.fields?.forEachIndexed { index, field ->
            when (val updatedField = updatedItems[index]) {
                is FormFieldTypeListItem.EditTextType -> field.value = updatedField.value
                is FormFieldTypeListItem.RadioTypeButton -> field.value = updatedField.value
                is FormFieldTypeListItem.RadioButton -> field.value =
                    updatedField.options.associate { it.itemName to it.isSelected }

                is FormFieldTypeListItem.DropDownList -> field.value =
                    updatedField.options.joinToString(",")
                // Add more cases as needed for other types
                is FormFieldTypeListItem.SignaturePadType -> field.value = ""
                is FormFieldTypeListItem.TableView -> field.value = ""
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun openBottomSheetDialogForSectionList() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_form_sections__dialog, null)

        val formSectionList = ArrayList<String>()
        for (i in 1..sectionArray.size) {
            formSectionList.add(sectionArray[i-1].displayName)
        }

        val formSectionsRecyclerView = view.findViewById<RecyclerView>(R.id.formSectionsRecyclerView)
        val chooseSection = view.findViewById<TextView>(R.id.choosesection)
        formSectionsRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = AdapterForFormSectionsList(formSectionList,dialog){sectionIndex,sectionName->
            saveCurrentSectionData()
            onChangeSectionIndexUpdateSectionItems(sectionIndex)
            currSectionIndex = sectionIndex
            binding.formSectionName.hint = sectionArray[currSectionIndex].displayName
        }
        formSectionsRecyclerView.adapter = adapter
        chooseSection.setOnClickListener{dialog.dismiss()}
        dialog.setContentView(view)
        dialog.show()

    }

}