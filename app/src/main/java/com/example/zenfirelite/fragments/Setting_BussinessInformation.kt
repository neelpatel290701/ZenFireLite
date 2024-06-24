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
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.zenfirelite.R
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.BussinessInformationResponse
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_Response
import com.example.zenfirelite.databinding.FragmentSettingBussinessInformationBinding
import com.example.zenfirelite.databinding.FragmentSettingUsersBinding
import com.example.zenfirelite.prefs
import com.example.zenfirelite.viewmodels.HomeViewModel
import com.example.zenfirelite.viewmodels.SettingViewModel
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

    private val viewModel : SettingViewModel by activityViewModels()

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


        viewModel.bussinessInfo.observe(viewLifecycleOwner, Observer { companyInfo ->
            if (companyInfo != null) {
                companyName.setText(companyInfo.result.name)
                companyTagLine.setText(companyInfo.result.tagLine)
                addressLine1.setText(companyInfo.result.addressLevel1)

                Glide.with(requireContext())
                    .load(companyInfo.result.media.file)
                    .into(companyLogo)
            }else{
                Toast.makeText(requireContext(), "BussinessInfo-Null", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }


}