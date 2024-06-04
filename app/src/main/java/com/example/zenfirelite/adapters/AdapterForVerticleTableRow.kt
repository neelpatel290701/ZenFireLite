package com.example.zenfirelite.adapters

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.TableRowTypesFields

class AdapterForVerticleTableRow(private val items: List<TableRowTypesFields>,
                                 private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class TableHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TableRowTypesFields.HeaderRow) {
            // Bind data to views
//            val textView = itemView.findViewById<TextView>(R.id.title)
//            textView.text = item.title
        }
    }

    inner class TableRowDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TableRowTypesFields.DataRow) {
            // Bind data to views
            val textView = itemView.findViewById<TextView>(R.id.srno_value)
            textView.text = item.title
//            val rowDataCellRecycleView = itemView.findViewById<RecyclerView>(R.id.rowDataCellRecycleView)
//            rowDataCellRecycleView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            val list = listOf(
//                "neel",
//                "Smit",
//                "dhruv",
//                "neel",
//                "Smit",
//                "dhruv",
//                )

//            val adapter = AdapterForHorizontalTableRowData(list)
//            rowDataCellRecycleView.adapter = adapter

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TableRowTypesFields.HeaderRow-> 0
            is TableRowTypesFields.DataRow -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> TableHeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.table_headerdata, parent, false)
            )
            1 -> TableRowDataViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.table_rowdata, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is TableRowTypesFields.HeaderRow -> (holder as TableHeaderViewHolder).bind(item)
            is TableRowTypesFields.DataRow -> (holder as TableRowDataViewHolder).bind(item)
        }
    }
}