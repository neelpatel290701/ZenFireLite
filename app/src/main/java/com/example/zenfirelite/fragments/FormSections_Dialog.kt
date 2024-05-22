package com.example.zenfirelite.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.zenfirelite.databinding.FragmentFormSectionsDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FormSections_Dialog : BottomSheetDialogFragment(){

    private lateinit var navController: NavController

    private lateinit var binding : FragmentFormSectionsDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireParentFragment().requireView())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentFormSectionsDialogBinding.inflate(inflater, container, false)

//        val formSectionList = ArrayList<String>()
//        for (i in 1..10) {
//            formSectionList.add("Neel Patel")
//            formSectionList.add("Kuldeep Tripathi")
//        }
//
//        binding.formSectionsRecyclerView.layoutManager = LinearLayoutManager(context)
//        val adapter = AdapterForFormSectionsList(formSectionList)
//
//        binding.formSectionsRecyclerView.adapter = adapter

        return binding.root
    }

}