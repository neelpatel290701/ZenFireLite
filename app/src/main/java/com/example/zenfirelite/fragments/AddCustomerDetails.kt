package com.example.zenfirelite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.FragmentAddCustomerDetailsBinding
import com.example.zenfirelite.databinding.FragmentCustomerDetailsBinding
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding


class AddCustomerDetails : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding : FragmentAddCustomerDetailsBinding
    private lateinit var navController: NavController
    var compnayTypes = arrayOf<String?>("Residential","Commercial")
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
        // Inflate the layout for this fragment
        binding = FragmentAddCustomerDetailsBinding.inflate(inflater, container, false)

        binding.spinner.onItemSelectedListener = this

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item,
            compnayTypes)
        ad.setDropDownViewResource(
            R.layout.status_spinner_dropdown_item)

        binding.spinner.adapter = ad

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}