package com.example.zenfirelite.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.UserAuth
import com.example.zenfirelite.apis.datamodels.UserAuthResponse
import com.example.zenfirelite.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var userName : String
    private lateinit var password : String
    private  var rememberMe : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            val isUserValid = validateUserAtLogin()
            if(isUserValid){
                loginUser()
            }
        }
    }

    private fun validateUserAtLogin() : Boolean {

        userName = binding.userName.text.toString().trim()
        password = binding.password.text.toString().trim()
        rememberMe = binding.toggle.isChecked

        if(userName.isEmpty()){
            binding.userName.error = "required"
            return false
        }
        if(!isValidUserName(userName)){
            binding.userName.error = "Invalid user name"
            return false
        }
        if(password.isEmpty()){
            binding.password.error = "required"
            return false
        }

        return true
    }

    private fun loginUser() {
        val userAuthModel = UserAuth(userName,password,true)
        APIManager.apiInterface.userAuth(userAuthModel)
            .enqueue(object : Callback<UserAuthResponse>{
                override fun onResponse(call: Call<UserAuthResponse>, response: Response<UserAuthResponse>) {
                    if(response.isSuccessful) {
                        val loginIntent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(loginIntent)
                        Log.i("neel", "response>>>>>>: ${response.body().toString()} ")
                    }else{
                        Toast.makeText(applicationContext, "User not found", Toast.LENGTH_LONG).show();
                    }
                }

                override fun onFailure(call: Call<UserAuthResponse>, t: Throwable) {
                        Log.d("neel","Login Response Failure")
                }
            })
    }
    private  fun isValidUserName(email  :String): Boolean {
        val emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        val emailPredicate = Regex(emailRegex)
        return emailPredicate.matches(email)
    }
}