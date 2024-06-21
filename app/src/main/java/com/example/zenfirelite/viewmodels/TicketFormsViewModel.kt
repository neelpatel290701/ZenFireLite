package com.example.zenfirelite.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.TicketFormsResponse
import com.example.zenfirelite.apis.datamodels.Term
import com.example.zenfirelite.apis.datamodels.TicketFormsRequestBody
import com.example.zenfirelite.datamodels.TicketFormListModel
import com.example.zenfirelite.prefs
import com.example.zenfirelite.utils.ZTUtils.convertIsoToCustomFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class TicketFormsViewModel : ViewModel() {

    private var _ticketFormsList = MutableLiveData<List<TicketFormListModel>?>()
    val ticketFormsList: LiveData<List<TicketFormListModel>?> get() = _ticketFormsList

    init {
        fetchTicketFormsList()
    }

    private fun fetchTicketFormsList() {

        val sortBy = "{\"fpFormUpdatedAt\":\"desc\"}"
        val ticketId = TicketFormsRequestBody(terms = listOf(Term(ticketId = listOf("1076503"))))

        APIManager.apiInterface.getTicketForms(
            sortBy,
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            ticketId
        ).enqueue(object : Callback<TicketFormsResponse>{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<TicketFormsResponse>,
                response: Response<TicketFormsResponse>
            ) {
                if(response.isSuccessful){
                    val ticketFormsResponse = response.body()
                    val ticketFormsList = ticketFormsResponse?.result?.hits?.map{ hit->
                        TicketFormListModel(
                            ticketId = hit.ticketId,
                            ticketNumber = hit.ticketNumber,
                            serviceAddressId = hit.serviceAddressId,
                            customerId = hit.customerId,
                            fpFormId = hit.fpFormId,
                            fpFormName = hit.fpFormName,
                            fpFormDisplayName = hit.fpFormDisplayName,
                            isAnalysed = hit.isAnalysed,
                            fpFormTemplateId = hit.fpFormTemplateId,
                            fpFormCreatedUserId = hit.fpFormCreatedUserId,
                            fpFormUpdatedUserId = hit.fpFormUpdatedUserId,
                            fpFormCreatedAt =  convertIsoToCustomFormat(hit.fpFormCreatedAt),
                            fpFormUpdatedAt =  convertIsoToCustomFormat(hit.fpFormUpdatedAt),
                            companyId = hit.companyId
                        )
                    }?.toCollection(ArrayList())

                    if (ticketFormsList != null) {
                        _ticketFormsList.value = ticketFormsList
                    } else {
                        _ticketFormsList.value = null
                    }
                }
            }

            override fun onFailure(call: Call<TicketFormsResponse>, t: Throwable) {
                Log.d("neel", "TicketFormsList-onFailure : $call")
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