package com.example.zenfirelite.utils

import android.util.Log
import com.example.zenfirelite.databinding.ActivityLoginBinding
import com.example.zenfirelite.databinding.FragmentAddCustomerDetailsBinding

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
}