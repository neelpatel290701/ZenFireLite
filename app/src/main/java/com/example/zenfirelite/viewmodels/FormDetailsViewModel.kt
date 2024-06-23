package com.example.zenfirelite.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.FormDetailsRequestBody
import com.example.zenfirelite.apis.datamodels.FormDetailsResponse
import com.example.zenfirelite.apis.datamodels.FormTemplatesListResponse
import com.example.zenfirelite.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FormDetailsViewModel : ViewModel() {

    private var _formDetails = MutableLiveData<FormDetailsResponse?>()
    val formDetails: LiveData<FormDetailsResponse?> get() = _formDetails


     fun fetchFormDetails(formId : String) {

        APIManager.apiInterface.getFormDetails(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            FormDetailsRequestBody(id = formId)
        ).enqueue(object : Callback<FormDetailsResponse>{
            override fun onResponse(
                call: Call<FormDetailsResponse>,
                response: Response<FormDetailsResponse>
            ) {
                if(response.isSuccessful){
                    _formDetails.value = response.body()
                }
            }

            override fun onFailure(call: Call<FormDetailsResponse>, t: Throwable) {
                Log.d("neel", "FormDetails-onFailure : $call")
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