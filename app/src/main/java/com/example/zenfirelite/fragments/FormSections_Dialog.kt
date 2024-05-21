package com.example.zenfirelite.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForFormSectionsList
import com.example.zenfirelite.databinding.FragmentFormDetailsBinding
import com.example.zenfirelite.databinding.FragmentFormSectionsDialogBinding
import com.example.zenfirelite.datamodels.InspectionInfoModel
import com.example.zenfirelite.interfaces.OnSectionSelectedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FormSections_Dialog : BottomSheetDialogFragment() ,OnSectionSelectedListener {

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

        val formSectionList = ArrayList<String>()
        for (i in 1..10) {
            formSectionList.add("Neel Patel")
            formSectionList.add("Kuldeep Tripathi")
        }

        binding.formSectionsRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = AdapterForFormSectionsList(formSectionList,this)

        binding.formSectionsRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onSectionSelected(index: Int) {
        Log.d("neel","Index issss : $index")
       val action = FormSections_DialogDirections.actionFormSectionsDialogToFormDetails2("neel",false,true,index)
        navController.navigate(action)
    }

}