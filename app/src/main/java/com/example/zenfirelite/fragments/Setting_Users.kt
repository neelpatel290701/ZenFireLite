package com.example.zenfirelite.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.FragmentSettingUsersBinding


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

        return binding.root
    }


}