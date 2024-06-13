package com.example.zenfirelite.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.zenfirelite.R
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.BussinessInformationResponse
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_Response
import com.example.zenfirelite.databinding.FragmentSettingBussinessInformationBinding
import com.example.zenfirelite.databinding.FragmentSettingUsersBinding
import com.example.zenfirelite.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Setting_BussinessInformation : Fragment() {

    private lateinit var binding : FragmentSettingBussinessInformationBinding

    private lateinit var companyName : EditText
    private lateinit var companyTagLine : EditText
    private lateinit var addressLine1 : EditText
    private lateinit var addressLine2 : EditText
    private lateinit var city : EditText
    private lateinit var state : EditText
    private lateinit var zipcode : EditText
    private lateinit var country : EditText
    private lateinit var phoneNumber : EditText
    private lateinit var companyLogo : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBussinessInformationBinding.inflate(inflater, container, false)

        companyName = binding.companyName
        companyTagLine = binding.companyTagline
        addressLine1 = binding.addressLine1
        addressLine2 = binding.addressLine2
        city = binding.city
        state = binding.state
        zipcode = binding.zipCode
        country = binding.country
        phoneNumber = binding.phoneNumber
        companyLogo = binding.companyLogo

        APIRequestForBussinessInfo()

        return binding.root
    }

    private fun APIRequestForBussinessInfo() {

        APIManager.apiInterface.getBussinessInfo(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            System.currentTimeMillis()
        ).enqueue(object : Callback<BussinessInformationResponse>{
            override fun onResponse(
                call: Call<BussinessInformationResponse>,
                response: Response<BussinessInformationResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("neel","${response.body()}")

                    val companyInfo = response.body()

                    companyName.setText(companyInfo?.result?.name)
                    companyTagLine.setText(companyInfo?.result?.tagLine)
                    addressLine1.setText(companyInfo?.result?.addressLevel1)

                    Glide.with(requireContext())
                        .load(companyInfo?.result?.media?.file)
                        .into(companyLogo)

                }else{
                    Log.d("neel","BussinessInfo-onResponse-Error")
                }
            }

            override fun onFailure(call: Call<BussinessInformationResponse>, t: Throwable) {
                Log.d("neel","BussinessInfo-onFailure")
            }
        })
    }

}