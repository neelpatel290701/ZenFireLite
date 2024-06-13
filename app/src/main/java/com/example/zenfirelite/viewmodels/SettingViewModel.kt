package com.example.zenfirelite.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.BussinessInformationResponse
import com.example.zenfirelite.datamodels.InspectionListModel
import com.example.zenfirelite.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingViewModel : ViewModel() {


    private var _bussinessInfo = MutableLiveData<BussinessInformationResponse?>()
    val bussinessInfo: LiveData<BussinessInformationResponse?> get() = _bussinessInfo

    init {
        fetchBussinessInfo()
    }

    private fun fetchBussinessInfo() {

        APIManager.apiInterface.getBussinessInfo(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            System.currentTimeMillis()
        ).enqueue(object : Callback<BussinessInformationResponse> {
            override fun onResponse(
                call: Call<BussinessInformationResponse>,
                response: Response<BussinessInformationResponse>
            ) {
                if (response.isSuccessful) {
                    _bussinessInfo.value = response.body()
                } else {
                    _bussinessInfo.value = null
                    Log.d("neel", "BussinessInfo-onResponse-Error")
                }
            }

            override fun onFailure(call: Call<BussinessInformationResponse>, t: Throwable) {
                Log.d("neel", "BussinessInfo-onFailure")
            }
        })
    }
}