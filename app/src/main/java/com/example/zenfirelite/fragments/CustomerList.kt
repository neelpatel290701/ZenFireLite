package com.example.zenfirelite.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenfirelite.adapters.AdapterForCustomerList
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.CustomerListRequestBody
import com.example.zenfirelite.apis.datamodels.CustomerListResponse
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_RequestBody
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_Response
import com.example.zenfirelite.databinding.FragmentCustomerListBinding
import com.example.zenfirelite.datamodels.CustomerListModel
import com.example.zenfirelite.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CustomerList : Fragment() {

    private lateinit var binding : FragmentCustomerListBinding
    private lateinit var navController: NavController
    private var customerList = ArrayList<CustomerListModel>()

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
        // Inflate the layout for this fragment
        binding = FragmentCustomerListBinding.inflate(inflater,container,false)

        binding.customerRecycleView.layoutManager = LinearLayoutManager(context)

        if(customerList.isEmpty()){
            fetchCustomerList()
        }else{
            val customerAdapter = context?.let { AdapterForCustomerList(customerList , it ,  false){
                    customerDetails->
                val action = CustomerListDirections.actionCustomerListToCustomerDetails(customerDetails)
                navController.navigate(action)

            } }
            binding.customerRecycleView.adapter = customerAdapter
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        parentFragmentManager.setFragmentResultListener(CUSTOMERDETAILS_KEY, this) { _, result ->
            val addedCustomerStatus = result.getBoolean(CUSTOMER_ADDED_STATUS)
            if(addedCustomerStatus) {
                fetchCustomerList()
            }
        }
    }

    private fun fetchCustomerList() {
        val customerListRequestModel2 = CustomerList_ServiceBilling_RequestBody(
            0,80,"","",false,false,true
        )

        APIManager.apiInterface.getCustomerListWithBillingService(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            customerListRequestModel2
        ).enqueue(object : Callback<CustomerList_ServiceBilling_Response>{
            override fun onResponse(
                call: Call<CustomerList_ServiceBilling_Response>,
                response: Response<CustomerList_ServiceBilling_Response>
            ) {
                val customerListResponse = response.body()
                val custList = customerListResponse?.result?.data?.hits?.flatMap{hit->
                        hit.serviceAddresses.map{ serviceAddress ->
                            CustomerListModel(
                                firstName = serviceAddress.firstname,
                                lastName = serviceAddress.lastname,
                                customerUniqueId = serviceAddress.customerUniqueId,
                                addressLine1 = serviceAddress.addressLine1,
                                addressLine2 = serviceAddress.addressLine2 ?: "", // Handle nullable field
                                city = serviceAddress.city,
                                state = serviceAddress.state,
                                zipCode = serviceAddress.zipcode,
                                email = serviceAddress.email,
                                cellPhone = serviceAddress.cellphone,
                                landline = serviceAddress.landline
                            )
                        }
                }?.toCollection(ArrayList())

                if(custList!=null){
                    customerList = custList
                }

                val customerAdapter = context?.let { AdapterForCustomerList(customerList , it ,  false){
                        customerDetails->
                    val action = CustomerListDirections.actionCustomerListToCustomerDetails(customerDetails)
                    navController.navigate(action)

                } }
                binding.customerRecycleView.adapter = customerAdapter

            }

            override fun onFailure(call: Call<CustomerList_ServiceBilling_Response>, t: Throwable) {
                Log.d("neel", "CustomerList-onFailure")
                t.printStackTrace()
            }
        })

    }


}




