package com.example.zenfirelite.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.InspectionListRequestBody
import com.example.zenfirelite.apis.datamodels.InspectionListResponse
import com.example.zenfirelite.apis.datamodels.SortBy
import com.example.zenfirelite.datamodels.InspectionListModel
import com.example.zenfirelite.prefs
import com.example.zenfirelite.utils.ZTUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HomeViewModel : ViewModel() {

    private var _inspectionList = MutableLiveData<List<InspectionListModel>?>()
    val inspectionList: LiveData<List<InspectionListModel>?> get() = _inspectionList

    init {
        fetchInspectionList()
    }

    private fun fetchInspectionList() {
        val inspectionListRequestModel = InspectionListRequestBody(SortBy("desc"))
        APIManager.apiInterface.inspectionList(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            inspectionListRequestModel
        ).enqueue(object : Callback<InspectionListResponse> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(
                    call: Call<InspectionListResponse>, response: Response<InspectionListResponse>
                ) {

                    if (response.isSuccessful) {
                        val InspectionResponse = response.body()
                        val insList = InspectionResponse?.result?.map { result ->
                            InspectionListModel(
                                result.ticketId.id,
                                "#" + result.ticketNumber,
                                result.ticketId.customerId.displayName,
                                result.status,
                                result.defficiencyReportedCount.toString(),
                                result.recommendationsCount.toString(),
                                ZTUtils.convertTimestampToFormattedDate(result.ticketStartDate),
                                ZTUtils.convertTimestampToFormattedDate(result.ticketEndDate),
                                "Kuldeep Tripathi",
                                result.ticketId.customerId.customerUniqueId,
                                result.ticketId.serviceAddressId.id,
                                result.ticketId.serviceAddressId.addressLine1 ?: "",
                                result.ticketId.serviceAddressId.addressLine2 ?: "",
                                result.ticketId.serviceAddressId.city,
                                result.ticketId.serviceAddressId.state,
                                result.ticketId.serviceAddressId.zipcode,
                                result.ticketId.serviceAddressId.country
                            )
                        }?.toCollection(ArrayList())
                        if (insList != null) {
                            _inspectionList.value = insList
                        } else {
                            _inspectionList.value = null
                        }
                    }
                }

                override fun onFailure(call: Call<InspectionListResponse>, t: Throwable) {
                    Log.d("neel", "InspectionList-onFailure : $call")
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