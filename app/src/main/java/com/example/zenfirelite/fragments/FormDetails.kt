package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val formName = args.formName.toString()
        requireActivity().title = "Alarm Inspection"

        currSectionIndex = args.sectionIndex
        Log.d("neel","-----$currSectionIndex")
        OnChangeSectionIndexUpdateSectionItems(currSectionIndex)

        val sectionName : String = "Profile"
        binding.formSectionName.hint =  sectionName

        binding.formSectionName.setOnClickListener {
//            val action = FormDetailsDirections.actionFormDetailsToFormSectionsDialog()
//            navController.navigate(action)
            val dialog = BottomSheetDialog(requireContext())
            // on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.fragment_form_sections__dialog, null)


            val formSectionList = ArrayList<String>()
            for (i in 1..10) {
                formSectionList.add("Neel Patel")
                formSectionList.add("Kuldeep Tripathi")
            }

            val formSectionsRecyclerView = view.findViewById<RecyclerView>(R.id.formSectionsRecyclerView)
            formSectionsRecyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = AdapterForFormSectionsList(formSectionList,dialog){sectionIndex,sectionName->
                OnChangeSectionIndexUpdateSectionItems(sectionIndex)
                binding.formSectionName.hint = sectionName
            }
            formSectionsRecyclerView.adapter = adapter

            dialog.setContentView(view)
            dialog.show()

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

        val list = mutableListOf(
            FieldTypeListItem.EditTextType("Enter First Name"),
            FieldTypeListItem.EditTextType("Enter Last Name"),
        )



        binding.datFieldRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter =  AdapterForDynamicDataField(list)
        binding.datFieldRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()



    }

    @SuppressLint("NotifyDataSetChanged")
    private fun OnChangeSectionIndexUpdateSectionItems(currSectionIndex : Int) {
                val tempList =  mutableListOf( FieldTypeListItem.EditTextType("Enter First Name"))

                for (i in 1.. currSectionIndex) {
                    tempList.add(FieldTypeListItem.EditTextType("Enter First Name"))
                }
                val adapter =  AdapterForDynamicDataField(tempList)
               binding.datFieldRecyclerView.adapter = adapter

                adapter.notifyDataSetChanged()

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