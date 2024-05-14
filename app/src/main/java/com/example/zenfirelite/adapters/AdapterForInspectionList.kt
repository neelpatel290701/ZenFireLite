package com.example.zenfirelite.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.activities.LoginActivity
import com.example.zenfirelite.datamodels.InspectionInfoModel
import com.example.zenfirelite.interfaces.OnItemClickListenerForInspectionItem

class AdapterForInspectionList(
    private val context: Context,
    private val inspectionInfoList:List<InspectionInfoModel>,
    private val itemClickListnerForInspectionItem: OnItemClickListenerForInspectionItem
) : RecyclerView.Adapter<AdapterForInspectionList.ViewHolder>() ,
    AdapterView.OnItemSelectedListener {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val customerName: TextView
        val InspectionNumber : TextView
//        val InspectionStatus : TextView
        val InsStatusOption : Spinner
        val DefReported  :TextView
        val Recommendation:TextView
        val InsStartDate : TextView
        val InsEndDate : TextView
        val InsStartTime : TextView
        val InsEndTime : TextView
        val InspectorName : TextView

        init {
            // Define click listener for the ViewHolder's View
            customerName = view.findViewById(R.id.customerName_value)
            InspectionNumber = view.findViewById(R.id.inspectionNumber_value)
//            InspectionStatus = view.findViewById(R.id.inspectionstatus_value)
            InsStatusOption = view.findViewById(R.id.statusSpinner)
            DefReported = view.findViewById(R.id.DefRep_value)
            Recommendation = view.findViewById(R.id.recommendation_value)
            InsStartDate = view.findViewById(R.id.InsStartDate_value)
            InsEndDate = view.findViewById(R.id.InsEndDate_value)
            InsStartTime = view.findViewById(R.id.InsStartTime_value)
            InsEndTime = view.findViewById(R.id.InsEndTime_value)
            InspectorName = view.findViewById(R.id.InspectorName_value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.inspection_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return inspectionInfoList.size
    }

    lateinit var myHolder : ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = inspectionInfoList[position]
        myHolder = holder
        holder.customerName.text = ItemsViewModel.CustomerName
        holder.InspectionNumber.text = ItemsViewModel.InspectionNumber
//        holder.InspectionStatus.text = ItemsViewModel.InspectionStatus
        holder.DefReported.text = ItemsViewModel.DeficiencyReported
        holder.Recommendation.text = ItemsViewModel.Recommendation
        holder.InsStartDate.text = ItemsViewModel.InsStartDate
        holder.InsEndDate.text = ItemsViewModel.InsEndDate
        holder.InsStartTime.text = ItemsViewModel.InsStartTime
        holder.InsEndTime.text = ItemsViewModel.InsEndTime
        holder.InspectorName.text = ItemsViewModel.InspectorName

        var status = arrayOf<String?>("In Process", "Completed","Pending")
        holder.InsStatusOption.onItemSelectedListener
        val insAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            context,
            R.layout.spinner_item,
            status)

        insAdapter.setDropDownViewResource(
            R.layout.status_spinner_dropdown_item)
        holder.InsStatusOption.adapter = insAdapter

        holder.itemView.setOnClickListener{
            itemClickListnerForInspectionItem.onItemClick(ItemsViewModel)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}