package com.example.zenfirelite.fragments

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

    private fun fetchCustomerList() {

        val customerListRequestModel = CustomerListRequestBody(
            0,10,"","","","","",
            false,"","","",
            true,false)

        APIManager.apiInterface.customerList(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            System.currentTimeMillis(),
            customerListRequestModel
        ).enqueue(object : Callback<CustomerListResponse>{

            override fun onResponse(
                call: Call<CustomerListResponse>,
                response: Response<CustomerListResponse>
            ) {
                val customerListResponse = response.body()
                val custList = customerListResponse?.result?.data?.hits?.map {hit->
                    CustomerListModel(
                        hit.firstname,hit.lastname,hit.customerUniqueId,hit.addressLine1,hit.addressLine2,
                        hit.city,hit.state,hit.zipcode,hit.email,hit.cellphone,hit.landline)
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

            override fun onFailure(call: Call<CustomerListResponse>, t: Throwable) {
                Log.d("neel", "CustomerList-onFailure")
                t.printStackTrace()
            }

        })
    }


}




