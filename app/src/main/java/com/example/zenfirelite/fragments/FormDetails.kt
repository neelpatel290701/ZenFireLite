package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
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
import com.example.zenfirelite.datamodels.FieldTypeListItem
import com.google.android.material.bottomsheet.BottomSheetDialog


class FormDetails : Fragment()  {


    private var currSectionIndex = 0

    private lateinit var binding : FragmentFormDetailsBinding
    private lateinit var navController: NavController
    val args : FormDetailsArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    @SuppressLint("SetTextI18n", "InflateParams", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentFormDetailsBinding.inflate(inflater, container, false)
        val formName = args.formName
        requireActivity().title = "Alarm Inspection"

        currSectionIndex = args.sectionIndex
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
            currSectionIndex++
            OnChangeSectionIndexUpdateSectionItems(currSectionIndex)
        }

        binding.peviousSection.setOnClickListener{
            currSectionIndex--
            OnChangeSectionIndexUpdateSectionItems(currSectionIndex)

        }

//        val list = mutableListOf(
//            FieldTypeListItem.EditTextType("Enter First Name","String"),
//            FieldTypeListItem.EditTextType("Enter Number","number"),
//        )

//        val options= arrayListOf("Neel Patel","kuldeep Tripathi","Smit Patel")
//
//        val list = mutableListOf(
//            FieldTypeListItem.EditTextType("First Name","String"),
//            FieldTypeListItem.DropDownList("Choose Option",options),
//            FieldTypeListItem.EditTextType("Phone No.","number"),
//        )



        binding.dataFieldRecyclerView.layoutManager = LinearLayoutManager(context)
        OnChangeSectionIndexUpdateSectionItems(currSectionIndex)
//        val adapter = context?.let { AdapterForDynamicDataField(list, it) }
//        binding.dataFieldRecyclerView.adapter = adapter
//        adapter!!.notifyDataSetChanged()



    }

    @SuppressLint("NotifyDataSetChanged")
    private fun OnChangeSectionIndexUpdateSectionItems(currSectionIndex : Int) {

                val options= arrayListOf("Neel Patel","kuldeep Tripathi","Smit Patel")
                val tempList =  mutableListOf( FieldTypeListItem.EditTextType("First Name","String"),
                    FieldTypeListItem.DropDownList("Choose Option",options),
                    FieldTypeListItem.EditTextType("Phone No.","number"),
                    FieldTypeListItem.RadioButton("Choose Any One",true),
                    FieldTypeListItem.RadioButton("Choose Multi Options",false),
                    FieldTypeListItem.RadioTypeButton("Select Option"))

                for (i in 1.. currSectionIndex) {
                    tempList.add(FieldTypeListItem.EditTextType("Enter First Name","String"))
                }
                val adapter = context?.let { AdapterForDynamicDataField(tempList, it) }
               binding.dataFieldRecyclerView.adapter = adapter

                adapter!!.notifyDataSetChanged()

              binding.formSectionName.hint = "Section "+ currSectionIndex.toString()

    }

    private fun openBottomSheetDialogForSectionList() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_form_sections__dialog, null)

        val formSectionList = ArrayList<String>()
        for (i in 1..10) {
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