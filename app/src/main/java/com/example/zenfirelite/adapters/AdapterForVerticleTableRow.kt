package com.example.zenfirelite.adapters

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.TableRowTypesFields

class AdapterForVerticleTableRow(private val items: List<TableRowTypesFields>) :
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
//            val textView = itemView.findViewById<TextView>(R.id.title)
//            textView.text = item.title
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