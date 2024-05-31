package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForDynamicDataField
import com.example.zenfirelite.adapters.AdapterForFormSectionsList
import com.example.zenfirelite.databinding.FragmentFormDetailsBinding
import com.example.zenfirelite.datamodels.Field
import com.example.zenfirelite.datamodels.FieldTypeListItem
import com.example.zenfirelite.datamodels.Option
import com.example.zenfirelite.datamodels.RadioButtonItem
import com.example.zenfirelite.datamodels.Section
import com.google.android.material.bottomsheet.BottomSheetDialog


class FormDetails : Fragment()  {

    companion object {
        const val SIGNATURE_RESULT_KEY = "signature_result_key"
        const val SIGNATURE_DATA_KEY = "signature_data_key"
    }


    private var currSectionIndex = 0

    private lateinit var binding : FragmentFormDetailsBinding
    private lateinit var navController: NavController
    val args : FormDetailsArgs by navArgs()


    val sections = arrayOf(
        Section(
            fields = listOf(
                Field(inputType = "text", title = "first name"),
                Field(inputType = "text", title = "last name"),
                Field(inputType = "number", title = "contact number"),
                Field(inputType = "text", title = "address"),
                Field(inputType = "number", title = "pincode"),
                Field(inputType = "text", title = "country"),
                Field(inputType = "textarea", title = "bio"),
                Field(inputType = "dropdownList", title = "country", options = listOf(
                    Option(value = "USA", isSelected = false),
                    Option(value = "Canada", isSelected = false),
                    Option(value = "Mexico", isSelected = false)
                )),
                Field(inputType = "dropdownList", title = "country", options = listOf(
                    Option(value = "USA", isSelected = false),
                    Option(value = "Canada", isSelected = false),
                    Option(value = "Mexico", isSelected = false)
                )),
                Field(inputType = "text", title = "country"),
                Field(inputType = "table", title = "Equipment Type"),
                Field(inputType = "text", title = "Service Type"),
                Field(inputType = "text", title = "City"),
                Field(inputType = "text", title = "country"),
            )
        ),
        Section(
            fields = listOf(
                Field(inputType = "dropdownList", title = "country", options = listOf(
                    Option(value = "USA", isSelected = false),
                    Option(value = "Canada", isSelected = false),
                    Option(value = "Mexico", isSelected = false)
                )),
                Field(inputType = "radio button", title = "gender", options = listOf(
                    Option(value = "Male", isSelected = false),
                    Option(value = "Female", isSelected = false),
                    Option(value = "Other", isSelected = false)
                )),
                Field(inputType = "checkbox", title = "subscribe to newsletter", options = listOf(
                    Option(value = "Yes", isSelected = false),
                    Option(value = "No", isSelected = false)
                )),
                Field(inputType = "table", title = "EquipmentInfo Type"),
                Field(inputType = "signature", title = "Inspector's Signature"),
                Field(inputType = "text", title = "password"),
                Field(inputType = "text", title = "email"),
                Field(inputType = "textarea", title = "comments")
            )
        ),
        Section(
            fields = listOf(
                Field(inputType = "radiotype button", title = "marital status", options = listOf(
                    Option(value = "Single", isSelected = false),
                    Option(value = "Married", isSelected = false),
                    Option(value = "Divorced", isSelected = false)
                )),
                Field(inputType = "textarea", title = "bio"),
                Field(inputType = "text", title = "favorite hobby"),
                Field(inputType = "text", title = "favorite food"),
                Field(inputType = "signature", title = "Inspector's Signature"),
                Field(inputType = "number", title = "number of siblings"),
                Field(inputType = "text", title = "mother's maiden name"),
                Field(inputType = "dropdownList", title = "favorite season", options = listOf(
                    Option(value = "Winter", isSelected = false),
                    Option(value = "Spring", isSelected = false),
                    Option(value = "Summer", isSelected = false),
                    Option(value = "Fall", isSelected = false)
                ))
            )
        ),
        Section(
            fields = listOf(
                Field(inputType = "text", title = "nickname"),
                Field(inputType = "text", title = "pet's name"),
                Field(inputType = "number", title = "house number"),
                Field(inputType = "text", title = "street name"),
                Field(inputType = "text", title = "city"),
                Field(inputType = "text", title = "state"),
                Field(inputType = "text", title = "zipcode")
            )
        ),
        Section(
            fields = listOf(
                Field(inputType = "dropdownList", title = "favorite color", options = listOf(
                    Option(value = "Red", isSelected = false),
                    Option(value = "Green", isSelected = false),
                    Option(value = "Blue", isSelected = false),
                    Option(value = "Yellow", isSelected = false)
                )),
                Field(inputType = "radio button", title = "subscription type", options = listOf(
                    Option(value = "Free", isSelected = false),
                    Option(value = "Premium", isSelected = false),
                    Option(value = "VIP", isSelected = false)
                )),
                Field(inputType = "checkbox", title = "features", options = listOf(
                    Option(value = "Feature 1", isSelected = false),
                    Option(value = "Feature 2", isSelected = false),
                    Option(value = "Feature 3", isSelected = false)
                )),
                Field(inputType = "textarea", title = "feedback"),
                Field(inputType = "text", title = "suggestions"),
                Field(inputType = "text", title = "improvements")
            )
        ),
        Section(
            fields = listOf(
                Field(inputType = "text", title = "first name"),
                Field(inputType = "text", title = "last name"),
                Field(inputType = "number", title = "phone number"),
                Field(inputType = "text", title = "emergency contact"),
                Field(inputType = "number", title = "emergency contact number"),
                Field(inputType = "text", title = "relationship"),
                Field(inputType = "textarea", title = "notes")
            )
        ),
        Section(
            fields = listOf(
                Field(inputType = "dropdownList", title = "city", options = listOf(
                    Option(value = "New York", isSelected = false),
                    Option(value = "Los Angeles", isSelected = false),
                    Option(value = "Chicago", isSelected = false),
                    Option(value = "Houston", isSelected = false)
                )),
                Field(inputType = "radio button", title = "age group", options = listOf(
                    Option(value = "18-24", isSelected = false),
                    Option(value = "25-34", isSelected = false),
                    Option(value = "35-44", isSelected = false),
                    Option(value = "45+", isSelected = false)
                )),
                Field(inputType = "radiotype button", title = "employment status"),
                Field(inputType = "text", title = "job title"),
                Field(inputType = "text", title = "company name"),
                Field(inputType = "number", title = "years of experience"),
                Field(inputType = "textarea", title = "professional summary")
            )
        ),
        Section(
            fields = listOf(
                Field(inputType = "text", title = "favorite book"),
                Field(inputType = "text", title = "favorite movie"),
                Field(inputType = "text", title = "favorite song"),
                Field(inputType = "number", title = "lucky number"),
                Field(inputType = "text", title = "favorite sport"),
                Field(inputType = "text", title = "favorite team"),
                Field(inputType = "textarea", title = "other interests")
            )
        )
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currSectionIndex = args.sectionIndex
    }
    @SuppressLint("SetTextI18n", "InflateParams", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentFormDetailsBinding.inflate(inflater, container, false)
        val formName = args.formName
        requireActivity().title = "Alarm Inspection"

        binding.formSectionName.hint = "Section "+ currSectionIndex.toString()

        binding.formSectionName.setOnClickListener {
            openBottomSheetDialogForSectionList()
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        binding.nextSection.setOnClickListener{
            if(currSectionIndex + 1 in sections.indices) {
                currSectionIndex++
                OnChangeSectionIndexUpdateSectionItems(currSectionIndex)
            }
        }

        binding.peviousSection.setOnClickListener{
            if(currSectionIndex - 1 in sections.indices) {
                currSectionIndex--
                OnChangeSectionIndexUpdateSectionItems(currSectionIndex)
            }

        }

        binding.dataFieldRecyclerView.layoutManager = LinearLayoutManager(context)


    }

    override fun onResume() {
        super.onResume()

        OnChangeSectionIndexUpdateSectionItems(currSectionIndex)

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

    @SuppressLint("NotifyDataSetChanged")
    private fun OnChangeSectionIndexUpdateSectionItems(currSectionIndex : Int) {

        when(currSectionIndex) {
            0 -> { binding.peviousSection.setTextColor(Color.parseColor("#D3D3D3"))
                   binding.nextSection.setTextColor(Color.parseColor("#318CE7"))
                 }
            sections.size-1 -> {
                  binding.nextSection.setTextColor(Color.parseColor("#D3D3D3"))
                  binding.peviousSection.setTextColor(Color.parseColor("#318CE7"))
                 }
            else -> {
                 binding.nextSection.setTextColor(Color.parseColor("#318CE7"))
                 binding.peviousSection.setTextColor(Color.parseColor("#318CE7"))
                 }
        }

        if (currSectionIndex in sections.indices) {

            val selectedSection = sections[currSectionIndex]
            // Transform the fields to FieldTypeListItem
            val fieldTypeListItems: List<FieldTypeListItem> = selectedSection.fields.map { field ->
                when (field.inputType) {
                    "text" -> FieldTypeListItem.EditTextType(field.title, "text")
                    "number" -> FieldTypeListItem.EditTextType(field.title,"number")
                    "dropdownList" -> FieldTypeListItem.DropDownList(field.title, field.options?.map { it.value } ?: emptyList())
                    "radio button" -> FieldTypeListItem.RadioButton(field.title, true, field.options?.map { RadioButtonItem(it.value, it.isSelected) } ?: emptyList())
                    "checkbox" -> FieldTypeListItem.RadioButton(field.title, false, field.options?.map { RadioButtonItem(it.value, it.isSelected) } ?: emptyList())
                    "radiotype button" -> FieldTypeListItem.RadioTypeButton(field.title)
                    "textarea" -> FieldTypeListItem.EditTextType(field.title, "textarea")
                    "signature" -> FieldTypeListItem.SignaturePadType(field.title)
                    "table" -> FieldTypeListItem.TableView(field.title)
                    else -> throw IllegalArgumentException("Unknown input type")
                }
            }

            val adapter = context?.let { AdapterForDynamicDataField(fieldTypeListItems, it) }
            binding.dataFieldRecyclerView.adapter = adapter
            adapter!!.notifyDataSetChanged()
            binding.formSectionName.hint = "Section "+ currSectionIndex.toString()

        } else {
            // Handle the case where the index is out of bounds
            Log.d("neel","Invalid Section Index, ")
        }

    }

    private fun openBottomSheetDialogForSectionList() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_form_sections__dialog, null)

        val formSectionList = ArrayList<String>()
        for (i in 1..sections.size) {
            formSectionList.add("Section "+(i-1).toString())
        }

        val formSectionsRecyclerView = view.findViewById<RecyclerView>(R.id.formSectionsRecyclerView)
        val chooseSection = view.findViewById<TextView>(R.id.choosesection)
        formSectionsRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = AdapterForFormSectionsList(formSectionList,dialog){sectionIndex,sectionName->
            OnChangeSectionIndexUpdateSectionItems(sectionIndex)
            currSectionIndex = sectionIndex
            binding.formSectionName.hint = "Section "+ currSectionIndex.toString()
        }
        formSectionsRecyclerView.adapter = adapter

        chooseSection.setOnClickListener{dialog.dismiss()}
        dialog.setContentView(view)
        dialog.show()

    }



//    @SuppressLint("InflateParams")
//    private fun addNewView() {
//        val inflater = LayoutInflater.from(requireContext()).inflate(R.layout.sample_textfieldtype_1, null)
//        binding.parentLinearLayout.addView(inflater, binding.parentLinearLayout.childCount)
//    }

//    private fun navigateToFormDetailsWithIndex(currSectionIndex: Int) {
//        val action = FormDetailsDirections.actionFormDetailsSelf("Neel",false,false,currSectionIndex)
//        navController.navigate(action)
//    }


}