package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.FragmentFormDetailsBinding
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding


class FormDetails : Fragment() {

    private lateinit var binding : FragmentFormDetailsBinding

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
        binding =  FragmentFormDetailsBinding.inflate(inflater, container, false)

        binding.buttonAdd.setOnClickListener{
            addNewView()
        }

        return binding.root
    }

    @SuppressLint("InflateParams")
    private fun addNewView() {
        val inflater = LayoutInflater.from(requireContext()).inflate(R.layout.sample_textfieldtype_1, null)
        binding.parentLinearLayout.addView(inflater, binding.parentLinearLayout.childCount)
    }

}