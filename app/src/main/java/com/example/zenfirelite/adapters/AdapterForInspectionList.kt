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

class AdapterForInspectionList(private val context: Context, private val inspectionInfoList:List<InspectionInfoModel>) :
    RecyclerView.Adapter<AdapterForInspectionList.ViewHolder>() ,
    AdapterView.OnItemSelectedListener {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val customerName: TextView
        val InspectionNumber : TextView
//        val InspectionStatus : TextView
        val statusOption : Spinner
        init {
            // Define click listener for the ViewHolder's View
            customerName = view.findViewById(R.id.customerName_value)
            InspectionNumber = view.findViewById(R.id.inspectionNumber_value)
//            InspectionStatus = view.findViewById(R.id.inspectionstatus_value)
            statusOption = view.findViewById(R.id.statusSpinner)
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


        var status = arrayOf<String?>("In Process", "Completed","Pending")
        holder.statusOption.onItemSelectedListener
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            context,
            android.R.layout.simple_spinner_item,
            status)

        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        holder.statusOption.adapter = ad

        holder.itemView.setOnClickListener{
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}