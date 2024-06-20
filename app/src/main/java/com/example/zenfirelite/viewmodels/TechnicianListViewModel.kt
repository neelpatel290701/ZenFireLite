package com.example.zenfirelite.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_RequestBody
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_Response
import com.example.zenfirelite.apis.datamodels.Technician
import com.example.zenfirelite.apis.datamodels.TechnicianListRequestBody
import com.example.zenfirelite.apis.datamodels.TechnicianListResponse
import com.example.zenfirelite.apis.datamodels.technicianOptions
import com.example.zenfirelite.datamodels.CustomerListModel
import com.example.zenfirelite.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class TechnicianListViewModel : ViewModel() {

    private var _technicianList = MutableLiveData<TechnicianListResponse?>()
    val technicianList: LiveData<TechnicianListResponse?> get() = _technicianList

    init {
        fetchTechnicianList()
    }

    private fun fetchTechnicianList() {

        val roleId = listOf<Long>( 3, 2, 4, 5, 0, 1, 6, 7)

        val TechnicianListRequestModel = TechnicianListRequestBody(true,
            technicianOptions(Technician(roleId)),
            listOf("technician")
        )

        APIManager.apiInterface.getTechnicianList(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            TechnicianListRequestModel
        ).enqueue(object : Callback<TechnicianListResponse> {
            override fun onResponse(
                call: Call<TechnicianListResponse>,
                response: Response<TechnicianListResponse>
            ) {
                if(response.isSuccessful){
                    _technicianList.value =  response.body()
                }
            }

            override fun onFailure(call: Call<TechnicianListResponse>, t: Throwable) {
                Log.d("neel", "TechicianList-onFailure : $call")
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