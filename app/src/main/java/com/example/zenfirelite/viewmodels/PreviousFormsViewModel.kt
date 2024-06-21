package com.example.zenfirelite.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.PreviousFormsResponse
import com.example.zenfirelite.datamodels.CreatedBy
import com.example.zenfirelite.datamodels.PreviousFormListModel
import com.example.zenfirelite.apis.datamodels.CreatedByResponse
import com.example.zenfirelite.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PreviousFormsViewModel : ViewModel() {

    private val _serviceAddressId = MutableLiveData<Long>()

    private var _previousFormsList = MutableLiveData<List<PreviousFormListModel>?>()
    val previousFormsList: LiveData<List<PreviousFormListModel>?> get() = _previousFormsList

    init {
        _serviceAddressId.observeForever{serviceAddId->
            fetchPreviousFormsList(serviceAddId)
        }
    }

    fun setServiceAddressId(serviceAddressId: Long) {
        _serviceAddressId.value = serviceAddressId
    }

    private fun fetchPreviousFormsList(serviceAdderessIdVal : Long) {

        val serviceAddressId = serviceAdderessIdVal
        val limit = 1000

        Log.d("neel","-----$serviceAddressId")

        APIManager.apiInterface.getPreviousForms(
            serviceAddressId,
            limit,
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString()
        ).enqueue(object : Callback<PreviousFormsResponse>{
            override fun onResponse(
                call: Call<PreviousFormsResponse>,
                response: Response<PreviousFormsResponse>
            ) {
                if(response.isSuccessful){
                    val previousFormsResponse = response.body()
                    val prevFormList = previousFormsResponse?.result?.results?.map{results->
                                PreviousFormListModel(
                                    id = results.id,
                                    name = results.name,
                                    displayName = results.displayName,
                                    isAnalysed =  results.isAnalysed,
                                    isActive = results.isActive,
                                    isDeleted = results.isDeleted,
                                    templateId = results.templateId,
                                    companyId = results.companyId,
                                    createdUserId = results.createdUserId,
                                    updatedUserId = results.updatedUserId,
                                    createdBy = results.createdBy.toCreatedBy(),
                                    updatedAt = results.updatedAt,
                                    createdAt = results.createdAt,
                                    updatedBy = results.updatedBy,
                                    ticketId = results.ticketId,
                                    ticketNumber = results.ticketNumber
                                )
                    }?.toCollection(ArrayList())

                    if(prevFormList != null){
                        _previousFormsList.value = prevFormList
                    }else{
                        _previousFormsList.value = null
                    }
                }
            }

            override fun onFailure(call: Call<PreviousFormsResponse>, t: Throwable) {
                Log.d("neel", "PreviousFormsList-onFailure : $call")
                if (t is IOException) {
                    Log.e("neel-RetrofitFailure", "Network error", t)
                } else {
                    Log.e("neel-RetrofitFailure", "Conversion error", t)
                }
                t.printStackTrace()
            }
        })
}

    fun CreatedByResponse.toCreatedBy() : CreatedBy {
        return CreatedBy(
            id = this.id ?: 0,
            firstName = this.firstName ?: "",
            lastName = this.lastName ?: "",
            username = this.username ?: "",
            email = this.email ?: "",
            isAdmin = this.isAdmin ?: false,
            roleId = this.roleId ?: 0,
            companyId = this.companyId ?: 0,
            timeZoneId = this.timeZoneId ?: "",
            isActive = this.isActive ?: false,
            isDeleted = this.isDeleted ?: false,
            updatedBy = this.updatedBy ?: 0,
            updatedAt = this.updatedAt ?: "",
            profilePhoto = this.profilePhoto ?: "",
            landline = this.landline ?: "",
            cellphone = this.cellphone ?: "",
            rateCriterion = this.rateCriterion ?: "",
            rateBasis = this.rateBasis ?: "",
            about = this.about ?: "",
            objectId = this.objectId ?: 0,
            createdAt = this.createdAt ?: ""
        )
    }

}