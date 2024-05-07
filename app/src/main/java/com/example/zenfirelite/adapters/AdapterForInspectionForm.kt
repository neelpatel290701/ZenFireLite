package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.InspectionInfoFormModel

class AdapterForInspectionForm(private val inspectionFormList : List<InspectionInfoFormModel>) :
    RecyclerView.Adapter<AdapterForInspectionForm.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val inspectionFormName : TextView
        val formInspectorName  : TextView
        val formInspectionDate : TextView
        val formInspectionTime : TextView
        init {
            inspectionFormName = view.findViewById(R.id.insFormName)
            formInspectorName = view.findViewById(R.id.formInspectorName_Value)
            formInspectionDate = view.findViewById(R.id.formInspectionDate_Value)
            formInspectionTime = view.findViewById(R.id.formInspectionTime_Value)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.inspectionform_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = inspectionFormList[position]
        holder.inspectionFormName.text = ItemsViewModel.inspectionFormName
        holder.formInspectorName.text = ItemsViewModel.formInspectorName
        holder.formInspectionDate.text = ItemsViewModel.formInspectionDate
        holder.formInspectionTime.text = ItemsViewModel.formInspectionTime
    }

    override fun getItemCount(): Int {
        return inspectionFormList.size
    }


}