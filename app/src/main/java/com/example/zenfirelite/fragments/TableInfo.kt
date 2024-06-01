package com.example.zenfirelite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForVerticleTableRow
import com.example.zenfirelite.databinding.FragmentFormDetailsBinding
import com.example.zenfirelite.databinding.FragmentTableInfoBinding
import com.example.zenfirelite.datamodels.TableRowTypesFields

class TableInfo : Fragment() {

    private lateinit var binding : FragmentTableInfoBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding =  FragmentTableInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.tableRowRecycleView.layoutManager = LinearLayoutManager(context)

        val list = mutableListOf(
            TableRowTypesFields.HeaderRow("Sr No."),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.HeaderRow("Sr No."),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.HeaderRow("Sr No."),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.HeaderRow("Sr No."),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            )


        val adapter = context?.let { AdapterForVerticleTableRow(list, it) }
        binding.tableRowRecycleView.adapter = adapter
    }

}