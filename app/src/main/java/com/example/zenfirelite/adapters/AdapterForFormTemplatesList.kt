package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import javax.xml.transform.Templates

class AdapterForFormTemplatesList(private val formTemplates : ArrayList<String>):
      RecyclerView.Adapter<AdapterForFormTemplatesList.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val formName : TextView = view.findViewById(R.id.formTemplateName)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterForFormTemplatesList.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.formtemplates_items_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterForFormTemplatesList.ViewHolder, position: Int) {
        val formName = formTemplates[position]
        holder.formName.text = formName
    }

    override fun getItemCount(): Int {
        return formTemplates.size
    }
}