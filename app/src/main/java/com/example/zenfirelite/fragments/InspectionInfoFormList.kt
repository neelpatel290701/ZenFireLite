package com.example.zenfirelite.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForFormTemplatesList
import com.example.zenfirelite.adapters.AdapterForInspectionForm
import com.example.zenfirelite.databinding.FragmentInspectionInfoFormListBinding
import com.example.zenfirelite.datamodels.InspectionInfoFormModel


class InspectionInfoFormList : Fragment() {
    private lateinit var binding : FragmentInspectionInfoFormListBinding
//    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        navController = Navigation.findNavController(view)
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


        binding.addForm.setOnClickListener{
                 OpenFormList()
        }

        return binding.root
    }

    private fun OpenFormList() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_addform_list)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialogeboxbackground)

        val dialogWidth = WindowManager.LayoutParams.MATCH_PARENT
        val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(900, 1300)

        dialog.window?.setGravity(Gravity.CENTER)

        val formTemplatesList = ArrayList<String>()
        for (i in 1..20) {
            formTemplatesList.add("Neel")
        }

        val formTemplatesRecycleView = dialog.findViewById<RecyclerView>(R.id.formTemplatesRecycleView)
        val formTemplatesRecycleView2 = dialog.findViewById<RecyclerView>(R.id.formTemplatesRecycleView2)
        formTemplatesRecycleView .layoutManager = LinearLayoutManager(requireContext())
        formTemplatesRecycleView2 .layoutManager = LinearLayoutManager(requireContext())
        val adapterform = AdapterForFormTemplatesList(formTemplatesList)
        formTemplatesRecycleView.adapter = adapterform
        formTemplatesRecycleView2.adapter = adapterform
        dialog.show()
    }


}

