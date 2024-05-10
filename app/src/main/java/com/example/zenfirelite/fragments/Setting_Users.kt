package com.example.zenfirelite.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForInspectionList
import com.example.zenfirelite.adapters.AdapterForSettingUsers
import com.example.zenfirelite.databinding.FragmentSettingUsersBinding
import com.example.zenfirelite.datamodels.InspectionInfoModel
import com.example.zenfirelite.datamodels.SettingUsersModel


class Setting_Users : Fragment() {

    private lateinit var binding : FragmentSettingUsersBinding

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
        binding = FragmentSettingUsersBinding.inflate(inflater, container, false)

        binding.userRecycleView.layoutManager = LinearLayoutManager(context)
        val userList = ArrayList<SettingUsersModel>()
        for (i in 1..5) {
            userList.add(
                SettingUsersModel("Neel Patel",
                    "neel.patel@zentrades.pro",
                    "12345",
                    "Inspector")

            )
            userList.add(
                SettingUsersModel("Kuldeep Tripathi",
                    "kuldeep@zentrades.pro",
                    "12345",
                    "Inspector")

            )
        }
        val adapter = AdapterForSettingUsers(userList)
        binding.userRecycleView.adapter = adapter

        return binding.root
    }


}