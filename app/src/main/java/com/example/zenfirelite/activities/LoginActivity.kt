package com.example.zenfirelite.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.UserAuthRequestBody
import com.example.zenfirelite.apis.datamodels.UserAuthResponse
import com.example.zenfirelite.databinding.ActivityLoginBinding
import com.example.zenfirelite.prefs
import com.example.zenfirelite.utils.ZTUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var userName : String
    private lateinit var password : String
    private  var rememberMe : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isUserLoggedIn()) {
            redirectToMainActivity()
        }

        binding.login.setOnClickListener {

            userName = binding.userName.text.toString().trim()
            password = binding.password.text.toString().trim()
            rememberMe = binding.toggle.isChecked

            val isValidUser = ZTUtils.validateUserAtLogin(userName,password,binding)
            if(isValidUser){
                binding.progessBar.visibility = View.VISIBLE
                loginUser(userName,password,rememberMe)
            }
        }
    }


    private fun loginUser(userName :String , password:String , rememberMe : Boolean) {
        val userAuthModel = UserAuthRequestBody(userName,password,rememberMe)
        APIManager.apiInterface.userAuth(userAuthModel)
            .enqueue(object : Callback<UserAuthResponse>{
                override fun onResponse(call: Call<UserAuthResponse>, response: Response<UserAuthResponse>) {
                    if(response.isSuccessful) {
                        val responseData = response.body()
                        redirectToMainActivity()
                        prefs.userID = responseData?.result?.user?.id.toString()
                        prefs.companyID = responseData?.result?.user?.company?.id.toString()
                        prefs.accessToken = responseData?.result?.accessToken
                        prefs.userName = responseData?.result?.user?.firstName +" "+responseData?.result?.user?.lastName
                    }else{
                        Toast.makeText(applicationContext, "User not found", Toast.LENGTH_LONG).show()
                    }
                    binding.progessBar.visibility = View.GONE
                }
                override fun onFailure(call: Call<UserAuthResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "UserAuthRequestBody Failure", Toast.LENGTH_LONG).show()
                    binding.progessBar.visibility = View.GONE
                    Log.d("neel", "Login-onFailure : $call")
                    if (t is IOException) {
                        Log.e("neel-RetrofitFailure", "Network error", t)
                    } else {
                        Log.e("neel-RetrofitFailure", "Conversion error", t)
                    }
                    t.printStackTrace()
                }
            })
    }

    private fun isUserLoggedIn(): Boolean {
        return prefs.userID != null && prefs.accessToken != null
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

}