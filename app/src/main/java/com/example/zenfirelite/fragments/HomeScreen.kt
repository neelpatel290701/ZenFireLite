package com.example.zenfirelite.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding
import java.text.DateFormatSymbols
import java.util.Calendar


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
            openCalenderPicker(binding.startDate)
        }
        binding.toDate.setOnClickListener {
            openCalenderPicker(binding.toDate)
        }
        return binding.root
    }

    private fun openCalenderPicker(dateValue: EditText) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = context?.let { it1 ->
            DatePickerDialog(
                it1,
                R.style.MyDatePickerDialogStyle,
                { view, year, monthOfYear, dayOfMonth ->
                    // Get the month name from the month number
                    val monthName = DateFormatSymbols().shortMonths[monthOfYear]
                    val date = "$dayOfMonth-$monthName-$year"
                    dateValue.setText(date)
                },
                year,
                month,
                day
            )
        }
        datePickerDialog?.show()
    }




}