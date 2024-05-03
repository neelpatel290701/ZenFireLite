package com.example.zenfirelite.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForInspectionList
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding
import com.example.zenfirelite.datamodels.InspectionInfoModel
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeScreen : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.startDate.setOnClickListener {
            openCalenderPicker(binding.startDate, null, binding.toDate.getDateInMillis())
        }
        binding.toDate.setOnClickListener {
            openCalenderPicker(binding.toDate, binding.startDate.getDateInMillis())
        }

        binding.inspectionrecyclerview.layoutManager = LinearLayoutManager(context)
        val inspectionList = ArrayList<InspectionInfoModel>()
        for (i in 1..20) {
            inspectionList.add(InspectionInfoModel(
                "#00123",
                "Neel Patel" ,
                "Completed",
                "0",
                "2",
                "05/02/2024",
                "05/03/2024",
                "12:00PM",
                "12:00PM",
                "Kuldeep Tripathi"
            ))
        }
        val adapter = context?.let { AdapterForInspectionList(it, inspectionList) }
        binding.inspectionrecyclerview.adapter = adapter


        return binding.root
    }

    private fun openCalenderPicker(
        dateValue: EditText,
        minDate: Long? = null,
        maxDate: Long? = null
    ) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog: DatePickerDialog? = null
        datePickerDialog = context?.let { it1 ->
            DatePickerDialog(
                it1,
                R.style.MyDatePickerDialogStyle,
                { view, year, monthOfYear, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, monthOfYear, dayOfMonth)
                    val selectedDate = calendar.time
                    val monthName = DateFormatSymbols().shortMonths[monthOfYear]
                    val date = "$dayOfMonth-$monthName-$year"
                    dateValue.setText(date)

                    if (dateValue.id == R.id.toDate) {
                        if (minDate != null) {
                            datePickerDialog?.datePicker?.minDate = minDate
                        }
                    } else if (dateValue.id == R.id.startDate) {
                        if (maxDate != null) {
                            datePickerDialog?.datePicker?.maxDate = maxDate
                        }
                    }
                },
                year,
                month,
                day
            )
        }
        if (minDate != null && datePickerDialog != null) {
            datePickerDialog.datePicker.minDate = minDate
        }
        if (maxDate != null && datePickerDialog != null) {
            datePickerDialog.datePicker.maxDate = maxDate
        }
        datePickerDialog?.show()
    }
    fun EditText.getDateInMillis(): Long? {
        val text = this.text.toString()
        if (text.isNotEmpty()) {
            val sdf = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.time = sdf.parse(text)
            return cal.timeInMillis
        }
        return null
    }


}