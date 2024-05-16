package com.example.zenfirelite.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import javax.xml.transform.Templates

class AdapterForFormTemplatesList(private val formTemplates : ArrayList<String>):
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

    override fun onBindViewHolder(holder: AdapterForFormTemplatesList.ViewHolder, position: Int) {
        val formName = formTemplates[position]
        holder.formName.text = formName

        holder.itemView.setOnClickListener {
//            Log.d("neel" ,formName)

        }
    }

    override fun getItemCount(): Int {
        return formTemplates.size
    }
}