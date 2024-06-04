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
import com.example.zenfirelite.adapters.AdapterForVerticleTableRow2
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
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding =  FragmentTableInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.tableRowRecycleView.layoutManager = object : LinearLayoutManager(context){
            override fun canScrollVertically(): Boolean {
                super.canScrollVertically()
                return false
            }
        }

        binding.tableRowRecycleView2.layoutManager = object : LinearLayoutManager(context){
            override fun canScrollVertically(): Boolean {
                super.canScrollVertically()
                return false
            }
        }

        val list = mutableListOf(
            TableRowTypesFields.HeaderRow("Sr No."),
            TableRowTypesFields.DataRow("1"),
            TableRowTypesFields.DataRow("2"),
            TableRowTypesFields.DataRow("3"),
            TableRowTypesFields.DataRow("4"),
            TableRowTypesFields.DataRow("5"),
            TableRowTypesFields.DataRow("6"),
            TableRowTypesFields.DataRow("7"),
            TableRowTypesFields.DataRow("8"),
            TableRowTypesFields.DataRow("9"),
            TableRowTypesFields.DataRow("10"),
            TableRowTypesFields.DataRow("11"),
            TableRowTypesFields.DataRow("12"),
            TableRowTypesFields.DataRow("13"),
            TableRowTypesFields.DataRow("14"),
            TableRowTypesFields.DataRow("15"),
            TableRowTypesFields.DataRow("16"),
            TableRowTypesFields.DataRow("17"),
            TableRowTypesFields.DataRow("18"),
            TableRowTypesFields.DataRow("19"),
            TableRowTypesFields.DataRow("20"),
            TableRowTypesFields.DataRow("21"),
            TableRowTypesFields.DataRow("22"),
            TableRowTypesFields.DataRow("23"),
            TableRowTypesFields.DataRow("24"),
            TableRowTypesFields.DataRow("25"),
            TableRowTypesFields.DataRow("26"),
            TableRowTypesFields.DataRow("27"),
            TableRowTypesFields.DataRow("28"),
            TableRowTypesFields.DataRow("29"),
            TableRowTypesFields.DataRow("30"),
            )


        val adapter = context?.let { AdapterForVerticleTableRow(list, it) }
        val adapter2 = context?.let { AdapterForVerticleTableRow2(list, it) }
        binding.tableRowRecycleView.adapter = adapter
        binding.tableRowRecycleView2.adapter = adapter2
    }

}