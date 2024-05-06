package com.example.zenfirelite.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForFireInspectorList
import com.example.zenfirelite.databinding.FragmentInspectionInfoBinding
import com.example.zenfirelite.datamodels.InspectionInfoModel

class InspectionInfo : Fragment() {

    private lateinit var binding : FragmentInspectionInfoBinding
    private lateinit var navController: NavController
    val args : InspectionInfoArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inspectionInfo : InspectionInfoModel = args.inspectionInfo
        // Set the title in the toolbar
        requireActivity().title = inspectionInfo.InspectionNumber
//        Log.d("neel" , "${inspectionInfo}")
        binding = FragmentInspectionInfoBinding.inflate(inflater,container,false)
//        binding.insInfoCustomerName.text = inspectionInfo.CustomerName.toString()

        binding.customerName.text = inspectionInfo.CustomerName.toString()
        binding.InsStartDateValue.text = inspectionInfo.InsStartDate
        binding.InsStartTimeValue.text = inspectionInfo.InsStartTime

        binding.location.setOnClickListener {
            val uri :  String = "http://maps.google.com/maps?saddr="
            val intent : Intent  = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        val fireInspectorList = ArrayList<String>()

        for (i in 1..50) {
            fireInspectorList.add("Neel Patel")
        }

        binding.fireInspector.setOnClickListener{

                val dialog = context?.let { it1 -> Dialog(it1) }
                dialog?.setContentView(R.layout.fireinspector_dialogspinner)
                dialog?.window?.setLayout(650,800)
                dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog?.show()

                val recyCleView = dialog?.findViewById<RecyclerView>(R.id.fireInspectorRecycleView)
                recyCleView?.layoutManager = LinearLayoutManager(context)
                val adapter = AdapterForFireInspectorList(fireInspectorList)

                recyCleView?.adapter = adapter
        }


        return binding.root
    }

}