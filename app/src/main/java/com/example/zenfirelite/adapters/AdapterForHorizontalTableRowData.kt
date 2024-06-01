package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R

class AdapterForHorizontalTableRowData(private val items: List<String>) :
    RecyclerView.Adapter<AdapterForHorizontalTableRowData.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       val rowData : TextView = view.findViewById(R.id.rowData)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_rowdata_cell_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.rowData.text = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

}