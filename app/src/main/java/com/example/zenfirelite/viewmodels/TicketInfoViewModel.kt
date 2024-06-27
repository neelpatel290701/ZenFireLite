package com.example.zenfirelite.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenfirelite.datamodels.FormTemplatesListModel
import com.example.zenfirelite.datamodels.InspectionListModel

class TicketInfoViewModel : ViewModel() {

    val ticketInfo: MutableLiveData<InspectionListModel> = MutableLiveData()

}