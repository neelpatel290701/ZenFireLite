package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.TicketFormListModel

class AdapterForInspectionForm(
    private val inspectionFormList : List<TicketFormListModel>,
    private val screenWidth: Int) :
    RecyclerView.Adapter<AdapterForInspectionForm.ViewHolder>(){

    private val ScreenSizethreshold = 1080

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val inspectionFormName : TextView
        val formInspectorName  : TextView
        val formInspectionDate : TextView
//        val formInspectionTime : TextView
        val lowerLayout : LinearLayout
        init {
            inspectionFormName = view.findViewById(R.id.insFormName)
            formInspectorName = view.findViewById(R.id.formInspectorName_Value)
            formInspectionDate = view.findViewById(R.id.formInspectionDate_Value)
//            formInspectionTime = view.findViewById(R.id.formInspectionTime_Value)
            lowerLayout = view.findViewById(R.id.insFormCardView_lowerLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.inspectionform_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = inspectionFormList[position]

        if (screenWidth > ScreenSizethreshold) {
            holder.lowerLayout.orientation = LinearLayout.HORIZONTAL
        } else {
            holder.lowerLayout.orientation = LinearLayout.VERTICAL
        }

        holder.inspectionFormName.text = ItemsViewModel.fpFormDisplayName
        holder.formInspectorName.text = "Neel Patel"
        holder.formInspectionDate.text = ItemsViewModel.fpFormCreatedAt
//        holder.formInspectionTime.text = ItemsViewModel.formInspectionTime
    }

    override fun getItemCount(): Int {
        return inspectionFormList.size
    }


}