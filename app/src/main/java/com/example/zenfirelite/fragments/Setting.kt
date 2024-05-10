package com.example.zenfirelite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.PageAdapterForInspectionInfo
import com.example.zenfirelite.adapters.PageAdapterForSetting
import com.example.zenfirelite.databinding.FragmentInspectionInfoBinding
import com.example.zenfirelite.databinding.FragmentSettingBinding
import com.google.android.material.tabs.TabLayoutMediator


class Setting : Fragment() {

    private lateinit var binding : FragmentSettingBinding
    private val tabTitles = arrayListOf("Bussiness Information","Users")
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
        binding = FragmentSettingBinding.inflate(inflater,container,false)


        binding.settingViewPager.adapter = PageAdapterForSetting(this)
        TabLayoutMediator(binding.settingTabLayout , binding.settingViewPager){tab,position->
            val customView = LayoutInflater.from(binding.settingTabLayout.context).
            inflate(R.layout.inspectioninfo_tabtitle, null) as TextView
            customView.text = tabTitles[position]
            tab.customView = customView
        }.attach()

        return binding.root
    }
}