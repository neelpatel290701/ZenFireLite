package com.example.zenfirelite.adapters

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.FormTemplatesListModel

class AdapterForFormTemplatesList(private val formTemplates: List<FormTemplatesListModel?>,
                                  private val dialog: Dialog,
                                  private val onItemClickListener: (FormTemplatesListModel,String) -> Unit):
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
        val formDetails = formTemplates[position]
        holder.formName.text = formDetails?.displayName

        holder.itemView.setOnClickListener {
              onItemClickListener.invoke(formDetails!!,holder.formName.text.toString())
              dialog.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return formTemplates.size
    }
}