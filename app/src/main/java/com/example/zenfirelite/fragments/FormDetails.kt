package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.FragmentFormDetailsBinding
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding



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
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentFormDetailsBinding.inflate(inflater, container, false)
        val formName = args.formName.toString()
        requireActivity().title = "Alarm Inspection"


        currSectionIndex = args.sectionIndex

        binding.buttonAdd.setOnClickListener{
            addNewView()
        }

        val sectionName : String = "Profile"
        binding.formSectionName.hint =  sectionName

        binding.index.text = currSectionIndex.toString()

        binding.formSectionName.setOnClickListener {
            val action = FormDetailsDirections.actionFormDetailsToFormSectionsDialog()
            navController.navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

       for (i in 1..currSectionIndex){
           addNewView()
       }

        binding.nextSection.setOnClickListener{
            currSectionIndex++
            navigateToFormDetailsWithIndex(currSectionIndex)
        }

        binding.peviousSection.setOnClickListener{
            currSectionIndex--
//            findNavController().popBackStack()
            navigateToFormDetailsWithIndex(currSectionIndex)

        }


    }





    @SuppressLint("InflateParams")
    private fun addNewView() {
        val inflater = LayoutInflater.from(requireContext()).inflate(R.layout.sample_textfieldtype_1, null)
        binding.parentLinearLayout.addView(inflater, binding.parentLinearLayout.childCount)
    }

    private fun navigateToFormDetailsWithIndex(currSectionIndex: Int) {
        val action = FormDetailsDirections.actionFormDetailsSelf("Neel",false,false,currSectionIndex)
        navController.navigate(action)
    }


}