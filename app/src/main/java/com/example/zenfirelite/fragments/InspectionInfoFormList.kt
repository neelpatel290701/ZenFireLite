package com.example.zenfirelite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForInspectionForm
import com.example.zenfirelite.databinding.FragmentInspectionInfoBinding
import com.example.zenfirelite.databinding.FragmentInspectionInfoFormListBinding
import com.example.zenfirelite.datamodels.InspectionInfoFormModel


class InspectionInfoFormList : Fragment() {
    private lateinit var binding : FragmentInspectionInfoFormListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInspectionInfoFormListBinding.inflate(inflater,container,false)

        val formInspectionList = ArrayList<InspectionInfoFormModel>()
        for (i in 1..10) {
            formInspectionList.add(
                InspectionInfoFormModel(
                "Inspection of IND Fire Suppression System",
                "Neel Patel",
                "07/05/2024",
                "12:00AM")
            )
            formInspectionList.add(
                InspectionInfoFormModel(
                "Off Road Vehicle Sysytem Inspection",
                "Kuldeep Tripathi",
                "07/05/2024",
                "12:00PM")
            )
        }

        val adapter = AdapterForInspectionForm(formInspectionList)
        binding.formsRecycleView.layoutManager = LinearLayoutManager(context)
        binding.formsRecycleView.adapter = adapter
        return binding.root
    }


}