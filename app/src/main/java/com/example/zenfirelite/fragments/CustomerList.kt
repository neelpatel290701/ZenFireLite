package com.example.zenfirelite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenfirelite.adapters.AdapterForCustomerList
import com.example.zenfirelite.databinding.FragmentCustomerListBinding
import com.example.zenfirelite.datamodels.CustomerListModel
import com.example.zenfirelite.viewmodels.CustomerListViewModel


class CustomerList : Fragment() {

    private lateinit var binding : FragmentCustomerListBinding
    private lateinit var navController: NavController

    private val customerListviewModel: CustomerListViewModel by activityViewModels()

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

        customerListviewModel.customerList.observe(viewLifecycleOwner, Observer { customerList ->
            if (customerList != null) {
                setRecyclerView(customerList)
            }else{
                Toast.makeText(requireContext(), "CustomerList-Null", Toast.LENGTH_SHORT).show()
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
                customerListviewModel.fetchCustomerList()
            }
        }
    }

}




