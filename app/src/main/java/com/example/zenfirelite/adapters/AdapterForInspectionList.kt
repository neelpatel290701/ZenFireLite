package com.example.zenfirelite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.InspectionListModel

class AdapterForInspectionList(
    private val context: Context,
    private val screenWidth: Int,
    private val inspectionInfoList:List<InspectionListModel>,
    private val onItemClickListener: (InspectionListModel) -> Unit
) : RecyclerView.Adapter<AdapterForInspectionList.ViewHolder>() ,
    AdapterView.OnItemSelectedListener {

    private val ScreenSizethreshold = 1080

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val customerName: TextView
        val InspectionNumber : TextView
        val InsStatusOptionSpinner : Spinner
        val DefReported  :TextView
        val Recommendation:TextView
        val InsStartDate : TextView
        val InsEndDate : TextView
        val InspectorName : TextView
        val insListCardviewLayout : LinearLayout

        init {
            // Define click listener for the ViewHolder's View
            customerName = view.findViewById(R.id.customerName_value)
            InspectionNumber = view.findViewById(R.id.inspectionNumber_value)
            InsStatusOptionSpinner = view.findViewById(R.id.statusSpinner)
            DefReported = view.findViewById(R.id.DefRep_value)
            Recommendation = view.findViewById(R.id.recommendation_value)
            InsStartDate = view.findViewById(R.id.InsStartDate_value)
            InsEndDate = view.findViewById(R.id.InsEndDate_value)
            InspectorName = view.findViewById(R.id.InspectorName_value)
            insListCardviewLayout = view.findViewById(R.id.insListCardViewLayout)
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


        if (screenWidth > ScreenSizethreshold) {
            holder.insListCardviewLayout.orientation = LinearLayout.HORIZONTAL
        } else {
            holder.insListCardviewLayout.orientation = LinearLayout.VERTICAL
        }

        myHolder = holder
        holder.customerName.text = ItemsViewModel.CustomerName
        holder.InspectionNumber.text = ItemsViewModel.InspectionNumber
        holder.DefReported.text = ItemsViewModel.DeficiencyReported
        holder.Recommendation.text = ItemsViewModel.Recommendation
        holder.InsStartDate.text = ItemsViewModel.InsStartDate
        holder.InsEndDate.text = ItemsViewModel.InsEndDate
        holder.InspectorName.text = ItemsViewModel.InspectorName

        val statusValue = ItemsViewModel.InspectionStatus

        val status = arrayOf<String?>("In Process", "Completed","Pending")
        val insAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            context,
            R.layout.spinner_item_inspectionstatus,
            status)

        insAdapter.setDropDownViewResource(
            R.layout.status_spinner_dropdown_item)
        holder.InsStatusOptionSpinner.adapter = insAdapter

//        holder.InsStatusOptionSpinner.setSelection(0)

        when(statusValue){
            "In Process" -> holder.InsStatusOptionSpinner.setSelection(0)
            "Completed" -> holder.InsStatusOptionSpinner.setSelection(1)
            "Pending"  -> holder.InsStatusOptionSpinner.setSelection(2)
        }

        holder.itemView.setOnClickListener{
//            itemClickListnerForInspectionItem.onItemClick(ItemsViewModel)
            onItemClickListener.invoke(ItemsViewModel)

        }


        holder.InsStatusOptionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (status[position]) {
                    "In Process" -> holder.InsStatusOptionSpinner.setBackgroundResource(R.drawable.shape_inprocess)
                    "Completed" -> holder.InsStatusOptionSpinner.setBackgroundResource(R.drawable.shape_completed)
                    "Pending"  -> holder.InsStatusOptionSpinner.setBackgroundResource(R.drawable.shape_pendding)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

    override fun onNothingSelected(parent: AdapterView<*>?) {}


}