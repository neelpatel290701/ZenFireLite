package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
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
import com.example.zenfirelite.datamodels.CustomerListModel
import com.example.zenfirelite.datamodels.InspectionListModel
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

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val customerDetails : CustomerListModel = args.customerDetails

        binding = FragmentCustomerDetailsBinding.inflate(inflater,container,false)
        binding.customerName.text = customerDetails.firstName + " " + customerDetails.lastName
        binding.customerUniqueId.text = customerDetails.customerUniqueId
        binding.customerAddress.text = customerDetails.addressLine1 + " " + customerDetails.addressLine2 + " " +
                                       customerDetails.city + " " + customerDetails.state + " " + customerDetails.zipCode

        binding.customerEmail.text = customerDetails.email
        binding.customerPhone.text = customerDetails.landline
        binding.customerCellPhone.text = customerDetails.cellPhone

        binding.customerDetailViewPager.adapter = PageAdapterForInspectionInfo(this)
        TabLayoutMediator(binding.customerDetailTabLayout , binding.customerDetailViewPager){tab,position->
              tab.text = tabTitles[position]
        }.attach()


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.cusDetailTopLayout.visibility = View.VISIBLE
    }


}