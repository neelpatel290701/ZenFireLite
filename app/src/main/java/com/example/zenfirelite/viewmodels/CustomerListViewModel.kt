package com.example.zenfirelite.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenfirelite.adapters.AdapterForCustomerList
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_RequestBody
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_Response
import com.example.zenfirelite.datamodels.CustomerListModel
import com.example.zenfirelite.datamodels.InspectionListModel
import com.example.zenfirelite.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CustomerListViewModel : ViewModel() {

    private var _customerList = MutableLiveData<List<CustomerListModel>?>()
    val customerList: LiveData<List<CustomerListModel>?> get() = _customerList

    init {
        fetchCustomerList()
    }

    fun fetchCustomerList() {
        val customerListRequestModel = CustomerList_ServiceBilling_RequestBody(
            0, 80, "", "", false, false, true
        )

        APIManager.apiInterface.getCustomerListWithBillingService(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            customerListRequestModel
        ).enqueue(object : Callback<CustomerList_ServiceBilling_Response> {
            override fun onResponse(
                call: Call<CustomerList_ServiceBilling_Response>,
                response: Response<CustomerList_ServiceBilling_Response>
            ) {
                if (response.isSuccessful) {
                    val customerListResponse = response.body()
                    val custList = customerListResponse?.result?.data?.hits?.flatMap { hit ->
                        hit.serviceAddresses.map { serviceAddress ->
                            CustomerListModel(
                                firstName = serviceAddress.firstname,
                                lastName = serviceAddress.lastname,
                                customerUniqueId = serviceAddress.customerUniqueId,
                                addressLine1 = serviceAddress.addressLine1,
                                addressLine2 = serviceAddress.addressLine2
                                    ?: "", // Handle nullable field
                                city = serviceAddress.city,
                                state = serviceAddress.state,
                                zipCode = serviceAddress.zipcode,
                                email = serviceAddress.email,
                                cellPhone = serviceAddress.cellphone,
                                landline = serviceAddress.landline
                            )
                        }
                    }?.toCollection(ArrayList())
                    if (custList != null) {
                        _customerList.value = custList
                    } else {
                        _customerList.value = null
                    }
                }

            }

            override fun onFailure(call: Call<CustomerList_ServiceBilling_Response>, t: Throwable) {
                Log.d("neel", "CustomerList-onFailure : $call")
                if (t is IOException) {
                    Log.e("neel-RetrofitFailure", "Network error", t)
                } else {
                    Log.e("neel-RetrofitFailure", "Conversion error", t)
                }
                t.printStackTrace()
            }
        })

    }
}