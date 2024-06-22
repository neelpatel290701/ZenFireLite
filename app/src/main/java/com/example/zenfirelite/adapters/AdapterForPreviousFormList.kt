package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.PreviousFormListModel
import com.example.zenfirelite.datamodels.TicketFormListModel

class AdapterForPreviousFormList(private val previousForms : List<PreviousFormListModel>,
                                 private val onItemClickListener: (PreviousFormListModel) -> Unit) :
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
        val formDetails = previousForms[position]
        holder.formName.text = formDetails.displayName

        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(formDetails)
        }
    }

    override fun getItemCount(): Int {
        return previousForms.size
    }
}