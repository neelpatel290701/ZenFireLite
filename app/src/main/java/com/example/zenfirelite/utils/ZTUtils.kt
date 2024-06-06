package com.example.zenfirelite.utils

import com.example.zenfirelite.databinding.ActivityLoginBinding

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

    private fun isValidUserName(email: String): Boolean {
        val emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        val emailPredicate = Regex(emailRegex)
        return emailPredicate.matches(email)
    }
}