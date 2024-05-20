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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.FragmentFormDetailsBinding
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding


class FormDetails : Fragment() {

    private lateinit var binding : FragmentFormDetailsBinding
    private lateinit var navController: NavController
    val args : FormDetailsArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentFormDetailsBinding.inflate(inflater, container, false)
        val formName = args.formName.toString()
//        Log.d("neel",formName)
        requireActivity().title = "Alarm Inspection"

        binding.buttonAdd.setOnClickListener{
            addNewView()
        }
        val sectionName : String = "Profile"
        binding.formSectionName.hint =  sectionName

        binding.formSectionName.setOnClickListener {
            val action = FormDetailsDirections.actionFormDetailsToFormSectionsDialog()
            navController.navigate(action)
        }

        binding.nextSection.setOnClickListener{

        }

        binding.peviousSection.setOnClickListener{

        }

        return binding.root
    }

    @SuppressLint("InflateParams")
    private fun addNewView() {
        val inflater = LayoutInflater.from(requireContext()).inflate(R.layout.sample_textfieldtype_1, null)
        binding.parentLinearLayout.addView(inflater, binding.parentLinearLayout.childCount)
    }

}