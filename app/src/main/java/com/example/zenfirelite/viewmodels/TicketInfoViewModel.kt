package com.example.zenfirelite.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenfirelite.datamodels.InspectionListModel

class TicketInfoViewModel : ViewModel() {

    private val _ticketInfo = MutableLiveData<InspectionListModel>()
    val ticketInfo: LiveData<InspectionListModel> get() = _ticketInfo

    fun setTicketInfo(data: InspectionListModel) {
        Log.d("neel","setTicketinfo")
        _ticketInfo.value = data
    }
}