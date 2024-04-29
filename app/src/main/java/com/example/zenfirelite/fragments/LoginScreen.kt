package com.example.zenfirelite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.FragmentLoginScreenBinding
import com.google.android.material.navigation.NavigationView

class LoginScreen : Fragment() {

    private lateinit var binding : FragmentLoginScreenBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginScreenBinding.inflate(inflater,container,false)

        binding.login.setOnClickListener {
                    navController.navigate(R.id.homeScreen)
        }


        return binding.root
    }
}