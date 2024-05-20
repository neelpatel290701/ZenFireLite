package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R

class AdapterForFormSectionsList(private val formSectionList: List<String>):
      RecyclerView.Adapter<AdapterForFormSectionsList.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val formSectionName: TextView = view.findViewById(R.id.formSectionName)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.formsections_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterForFormSectionsList.ViewHolder, position: Int) {
        val ItemsViewModel = formSectionList[position]
        holder.formSectionName.text = ItemsViewModel
    }

    override fun getItemCount(): Int {
        return formSectionList.size
    }
}