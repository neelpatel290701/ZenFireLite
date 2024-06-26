package com.example.zenfirelite.utils

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.ActivityLoginBinding
import com.example.zenfirelite.databinding.FragmentAddCustomerDetailsBinding
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Locale

object ZTUtils {

    fun validateUserAtLogin(
        userName: String,
        password: String,
        binding: ActivityLoginBinding
    ): Boolean {
        if (userName.isEmpty()) {
            binding.userName.error = "required"
            return false
        }
        if (!isValidUserName(userName)) {
            binding.userName.error = "Invalid user name"
            return false
        }
        if (password.isEmpty()) {
            binding.password.error = "required"
            return false
        }
        return true
    }

    fun isValidUserName(email: String): Boolean {
        val emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        val emailPredicate = Regex(emailRegex)
        return emailPredicate.matches(email)
    }

    fun validateCustomerDetails(
        companyName: String,
        firstName: String,
        addressLine1: String,
        city: String,
        state: String,
        zipCode: String,
        country: String,
        email: String,
        binding: FragmentAddCustomerDetailsBinding
    ): Boolean {

        if (companyName.isEmpty()) {
            binding.companyName.error = "Required"
            return false
        }
        if (firstName.isEmpty()) {
            binding.firstName.error = "Required"
            return false
        }
        if (addressLine1.isEmpty()) {
            binding.addressLine1.error = "Required"
            return false
        }
        if (city.isEmpty()) {
            binding.city.error = "Required"
            return false
        }
        if (state.isEmpty()) {
            binding.state.error = "Required"
            return false
        }
        if (zipCode.isEmpty()) {
            binding.zipCode.error = "Required"
            return false
        }
        if (country.isEmpty()) {
            binding.country.error = "Required"
            return false
        }
        if (email.isEmpty()) {
            binding.email.error = "Required"
            return false
        }

        if (!isValidUserName(email)) {
            binding.email.error = "check email"
            return false
        }

        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimestampToFormattedDate(timestamp: Long): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy   hh:mma")

        return dateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertIsoToCustomFormat(isoDateTime: String): String {
        if (isoDateTime.isEmpty()) {
            // Handle the empty string case, return an empty string
            return ""
        }

        return try {
            val zonedDateTime = ZonedDateTime.parse(isoDateTime)
            val systemDefaultZone = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy   hh:mma")
            systemDefaultZone.format(formatter)
        } catch (e: DateTimeParseException) {
            // Handle parsing error, return an empty string
            ""
        }
    }



    @Suppress("NAME_SHADOWING")
    fun openCalenderPicker(
        dateValue: EditText,
        minDate: Long? = null,
        maxDate: Long? = null,
        context : Context
    ) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog: DatePickerDialog? = null
        datePickerDialog = DatePickerDialog(
            context,
            R.style.MyDatePickerDialogStyle,
            { _, year, monthOfYear, dayOfMonth ->
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
        if (minDate != null) {
            datePickerDialog.datePicker.minDate = minDate
        }
        if (maxDate != null) {
            datePickerDialog.datePicker.maxDate = maxDate
        }
        datePickerDialog.show()
    }

    fun EditText.getDateInMillis(): Long? {
        val text = this.text.toString()
        if (text.isNotEmpty()) {
            val sdf = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.time = sdf.parse(text)!!
            return cal.timeInMillis
        }
        return null
    }

}