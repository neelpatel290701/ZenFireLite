package com.example.zenfirelite.fragments

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
import com.example.zenfirelite.R
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
        Log.d("neel" , "${inspectionInfo}")
        binding = FragmentInspectionInfoBinding.inflate(inflater,container,false)
        binding.insInfoCustomerName.text = inspectionInfo.CustomerName.toString()
        return binding.root
    }

}