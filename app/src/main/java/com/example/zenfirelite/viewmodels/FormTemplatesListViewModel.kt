package com.example.zenfirelite.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.FormTemplatesListResponse
import com.example.zenfirelite.datamodels.Column
import com.example.zenfirelite.datamodels.FieldOptions
import com.example.zenfirelite.datamodels.FieldType
import com.example.zenfirelite.datamodels.FormTemplatesListModel
import com.example.zenfirelite.datamodels.Layout
import com.example.zenfirelite.datamodels.SectionData
import com.example.zenfirelite.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FormTemplatesListViewModel : ViewModel() {

    private var _formTemplatesList = MutableLiveData<FormTemplatesListModel?>()
    val formTemplatesList: LiveData<FormTemplatesListModel?> get() = _formTemplatesList

    init {
        fetchFormTemplatesList()
    }

    private fun fetchFormTemplatesList(){

        APIManager.apiInterface.getFormTemplates(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
        ).enqueue(object : Callback<FormTemplatesListResponse> {
            override fun onResponse(
                call: Call<FormTemplatesListResponse>,
                response: Response<FormTemplatesListResponse>
            ) {
                if (response.isSuccessful) {
                        Log.d("neel","${response.body()}")
                }
            }

            override fun onFailure(call: Call<FormTemplatesListResponse>, t: Throwable) {
                Log.d("neel", "FormTemplatesList-onFailure : $call")
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