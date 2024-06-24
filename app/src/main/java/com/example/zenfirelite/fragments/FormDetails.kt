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
        currSectionIndex = args.sectionIndex
    }
    @SuppressLint("SetTextI18n", "InflateParams", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentFormDetailsBinding.inflate(inflater, container, false)

        if(args.isFromFormTemplate) {

            formTemplateDetailsViewModel.formTemplateDetails.observe(viewLifecycleOwner, Observer {
                val sectionList: List<SectionData> = it?.sections ?: emptyList()
                sectionArray = sectionList.toTypedArray()
                Log.d("neel", "FormTemplate - Observer triggered, sectionArray size: ${sectionArray.size}")
                if (sectionArray.isNotEmpty()) {
                    updateUI()
                }
            })

        }else{
            previousFormDetailsviewModel.formDetails.observe(viewLifecycleOwner, Observer {
                val sectionList: List<SectionData> = it?.result?.sections ?: emptyList()
                sectionArray = sectionList.toTypedArray()
                Log.d("neel", "Previous Form - Observer triggered, sectionArray size: ${sectionArray.size}")
                if (sectionArray.isNotEmpty()) {
                    updateUI()
                }
            })

        }

        setHasOptionsMenu(true)

        val formName = args.formName
        requireActivity().title = formName

        return binding.root
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
            if (currSectionIndex + 1 in sectionArray.indices) {
                currSectionIndex++
                onChangeSectionIndexUpdateSectionItems(currSectionIndex)
            }
        }

        binding.peviousSection.setOnClickListener {
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

            val fieldTypeListItems2: List<FormFieldTypeListItem> =
                selectedSectionArray.fields?.map { field ->
                    when (field.uiType) {
                        "AUTO_POPULATE" -> FormFieldTypeListItem.EditTextType(field.displayName, field.dataType,false)
                        "INPUT" -> FormFieldTypeListItem.EditTextType(field.displayName,field.dataType,false)
                        "TEXTAREA"->FormFieldTypeListItem.EditTextType(field.displayName,field.dataType,true)
                        "DROPDOWN" -> FormFieldTypeListItem.DropDownList(field.displayName, field.options?.dropdownOptions?.map{it.value} ?: emptyList())
                        "CHECKBOX" -> FormFieldTypeListItem.RadioButton(field.displayName, false, field.options?.checkboxOptions?.map { RadioButtonItem(it.value,false) }?: emptyList())
                        "RADIO" -> FormFieldTypeListItem.RadioButton(field.displayName, true, field.options?.radioOptions?.map { RadioButtonItem(it.value,false) }?: emptyList())
                        "BUTTON_RADIO" -> FormFieldTypeListItem.RadioTypeButton(field.displayName)
                        "TABLE" -> FormFieldTypeListItem.TableView(field.displayName)
                        "SIGNATURE_PAD" -> FormFieldTypeListItem.SignaturePadType(field.displayName)
                        else -> FormFieldTypeListItem.EditTextType(field.displayName, "text",false)
                    }
                } ?: emptyList()

            val adapter = context?.let { AdapterForDynamicDataField(fieldTypeListItems2, it) }
            binding.dataFieldRecyclerView.adapter = adapter
            adapter!!.notifyDataSetChanged()
            binding.formSectionName.hint = selectedSectionArray.displayName

        }else{
            // Handle the case where the index is out of bounds
            Log.d("neel","Invalid Section Index, ")
        }

    }

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