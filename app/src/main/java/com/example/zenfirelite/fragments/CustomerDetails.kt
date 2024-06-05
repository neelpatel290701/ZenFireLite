package com.example.zenfirelite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.PageAdapterForInspectionInfo
import com.example.zenfirelite.databinding.FragmentCustomerDetailsBinding
import com.example.zenfirelite.databinding.FragmentCustomerListBinding
import com.google.android.material.tabs.TabLayoutMediator


class CustomerDetails : Fragment() {

    private lateinit var binding : FragmentCustomerDetailsBinding
    val args : CustomerDetailsArgs by navArgs()
    private val tabTitles = arrayListOf("Forms","Deficiency")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCustomerDetailsBinding.inflate(inflater,container,false)
        binding.customerName.text = args.cutomerName
        binding.customerUniqueId.text = args.cutomerUniqueId

        binding.customerDetailViewPager.adapter = PageAdapterForInspectionInfo(this)
        TabLayoutMediator(binding.customerDetailTabLayout , binding.customerDetailViewPager){tab,position->
              tab.text = tabTitles[position]
//            val customView = LayoutInflater.from(binding.customerDetailTabLayout.context).
//            inflate(R.layout.inspectioninfo_tabtitle, null) as TextView
//            customView.text = tabTitles[position]
//            tab.customView = customView
        }.attach()


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.cusDetailTopLayout.visibility = View.VISIBLE
    }


}