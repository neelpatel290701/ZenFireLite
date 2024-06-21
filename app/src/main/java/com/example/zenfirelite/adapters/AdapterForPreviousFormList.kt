package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.PreviousFormListModel

class AdapterForPreviousFormList(private val previousForms : List<PreviousFormListModel>) :
    RecyclerView.Adapter<AdapterForPreviousFormList.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val formName : TextView = view.findViewById(R.id.formName)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.formtemplates_items_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterForPreviousFormList.ViewHolder, position: Int) {
        val formName = previousForms[position]
        holder.formName.text = formName.displayName
    }

    override fun getItemCount(): Int {
        return previousForms.size
    }
}