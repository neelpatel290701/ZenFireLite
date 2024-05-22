package com.example.zenfirelite.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.FragmentAddCustomerDetailsBinding
import com.example.zenfirelite.databinding.FragmentCustomerDetailsBinding
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding


@Suppress("DEPRECATION")
class AddCustomerDetails : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding : FragmentAddCustomerDetailsBinding
    private lateinit var navController: NavController
    var compnayTypes = arrayOf<String?>("Residential","Commercial")
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
        binding = FragmentAddCustomerDetailsBinding.inflate(inflater, container, false)

        binding.spinner.onItemSelectedListener = this
        setHasOptionsMenu(true)

        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item,
            compnayTypes)

        adapter.setDropDownViewResource(
            R.layout.status_spinner_dropdown_item)

        binding.spinner.adapter = adapter

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbaritems, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem_save = menu.findItem(R.id.save_addCustomer)
        menuItem_save.isVisible = true //ion
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when (item.itemId) {
            R.id.save_addCustomer -> {
                Toast.makeText(requireContext(), "Customer Added", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
                true
            }
            else -> false
        }
    }



    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}