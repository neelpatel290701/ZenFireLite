package com.example.zenfirelite.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenfirelite.adapters.AdapterForCustomerList
import com.example.zenfirelite.adapters.AdapterForInspectionList
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.CustomerListRequestBody
import com.example.zenfirelite.apis.datamodels.CustomerListResponse
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_RequestBody
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_Response
import com.example.zenfirelite.databinding.FragmentCustomerListBinding
import com.example.zenfirelite.datamodels.CustomerListModel
import com.example.zenfirelite.prefs
import com.example.zenfirelite.viewmodels.CustomerListViewModel
import com.example.zenfirelite.viewmodels.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class CustomerList : Fragment() {

    private lateinit var binding : FragmentCustomerListBinding
    private lateinit var navController: NavController

    private val viewModel: CustomerListViewModel by viewModels()

    companion object {
        const val CUSTOMERDETAILS_KEY = "customerDetails_key"
        const val CUSTOMER_ADDED_STATUS = "customer_Added_status"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.addCustomer.setOnClickListener {
            val action = CustomerListDirections.actionCustomerListToAddCustomerDetails()
            navController.navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCustomerListBinding.inflate(inflater,container,false)

        binding.customerRecycleView.layoutManager = LinearLayoutManager(context)

        viewModel.customerList.observe(viewLifecycleOwner, Observer { customerList ->
            if (customerList != null) {
                setRecyclerView(customerList)
            }
        })
        return binding.root
    }

    private fun setRecyclerView(customerList: List<CustomerListModel>) {

        val customerAdapter = context?.let { AdapterForCustomerList(customerList , it ,  false){
                customerDetails->
            val action = CustomerListDirections.actionCustomerListToCustomerDetails(customerDetails)
            navController.navigate(action)
        } }
        binding.customerRecycleView.adapter = customerAdapter
    }

    override fun onResume() {
        super.onResume()
        parentFragmentManager.setFragmentResultListener(CUSTOMERDETAILS_KEY, this) { _, result ->
            val addedCustomerStatus = result.getBoolean(CUSTOMER_ADDED_STATUS)
            if(addedCustomerStatus) {
                viewModel.fetchCustomerList()
            }
        }
    }

}




