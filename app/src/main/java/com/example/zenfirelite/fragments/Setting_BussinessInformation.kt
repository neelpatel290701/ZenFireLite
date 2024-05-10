package com.example.zenfirelite.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.FragmentSettingBussinessInformationBinding
import com.example.zenfirelite.databinding.FragmentSettingUsersBinding


class Setting_BussinessInformation : Fragment() {

    private lateinit var binding : FragmentSettingBussinessInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBussinessInformationBinding.inflate(inflater, container, false)

//        if (!TextUtils.isEmpty(binding.userName.getText())) {
//            // Set hint to empty to prevent floating hint from showing
//            binding.neel.setHint("Company Name");
//        }
//        // Set hint if EditText is empty
//        else {
//            binding.neel.setHint("Comapny Name");
//        }

        return binding.root
    }

}