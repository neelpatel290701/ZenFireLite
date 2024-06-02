package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R

class AdapterForHorizontalTableHeaderData(private val items: List<String>):
    RecyclerView.Adapter<AdapterForHorizontalTableHeaderData.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerData : TextView = view.findViewById(R.id.headerData)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_headerdata_cell_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AdapterForHorizontalTableHeaderData.ViewHolder,
        position: Int
    ) {
        val item = items[position]
        holder.headerData.text = item
    }

    override fun getItemCount(): Int {
        return  items.size
    }
}