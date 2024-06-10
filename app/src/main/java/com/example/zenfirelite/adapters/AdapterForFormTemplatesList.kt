package com.example.zenfirelite.adapters

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.interfaces.OnItemClickListenerForFormTemplateItem

class AdapterForFormTemplatesList(private val formTemplates : ArrayList<String> ,
        private val itemClickListenerForFormTemplateItem: OnItemClickListenerForFormTemplateItem,
        private val dialog: Dialog):
        RecyclerView.Adapter<AdapterForFormTemplatesList.ViewHolder>(){

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val formName = formTemplates[position]
        holder.formName.text = formName

        holder.itemView.setOnClickListener {
              itemClickListenerForFormTemplateItem.onFormTemplateClick(holder.formName.toString())
              dialog.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return formTemplates.size
    }
}