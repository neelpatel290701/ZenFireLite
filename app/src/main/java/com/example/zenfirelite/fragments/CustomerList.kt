package com.example.zenfirelite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForCustomerList
import com.example.zenfirelite.databinding.FragmentCustomerListBinding
import com.example.zenfirelite.databinding.FragmentInspectionInfoBinding


class CustomerList : Fragment() {

    private lateinit var binding : FragmentCustomerListBinding
    private lateinit var navController: NavController
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
        binding = FragmentCustomerListBinding.inflate(inflater,container,false)

        val customerList = ArrayList<String>()
        for (i in 1..10) {
            customerList.add("Neel Patel (1234)")
            customerList.add("Smit Patel (5678)")
            customerList.add("Kuldeep Tripathi (9012)")
            customerList.add("Dhruv Pathak (34567)")
        }

        val customerAdapter = context?.let { AdapterForCustomerList(customerList , it) }
        binding.customerRecycleView.layoutManager = LinearLayoutManager(context)
        binding.customerRecycleView.adapter = customerAdapter

        binding.addCustomer.setOnClickListener {
            val action = CustomerListDirections.actionCustomerListToAddCustomerDetails()
            navController.navigate(action)
        }

        return binding.root
    }

}