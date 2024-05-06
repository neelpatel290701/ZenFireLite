package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R

class AdapterForFireInspectorList(private val inspectorName : List<String>):
      RecyclerView.Adapter<AdapterForFireInspectorList.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val inspectorName: TextView

        init {
            // Define click listener for the ViewHolder's View
            inspectorName = view.findViewById(R.id.inspectorNameOption)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fireinspector_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = inspectorName[position]
        holder.inspectorName.text = ItemsViewModel
    }

    override fun getItemCount(): Int {
        return inspectorName.size
    }


}