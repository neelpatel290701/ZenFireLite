package com.example.zenfirelite.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.fragments.CustomerListDirections

class AdapterForCustomerList(private val customerNameList: List<String> , private val context : Context) :
    RecyclerView.Adapter<AdapterForCustomerList.ViewHolder>() {

    private var expandedPosition = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val customerName: TextView = view.findViewById(R.id.customerName)
        val customerNameRep: TextView = view.findViewById(R.id.customerNameRep)
        val customerNameView2: LinearLayout = view.findViewById(R.id.customerNameView2)
        val customerNameView1: CardView = view.findViewById(R.id.customerNameView1)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.customer_cardview, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = customerNameList[position]

        holder.customerName.text = item
        holder.customerNameRep.text = item

        val isExpanded = position == expandedPosition

        holder.customerNameView2.visibility = if (isExpanded) View.VISIBLE else View.GONE

        holder.customerNameView1.setOnClickListener {
            if (expandedPosition != position) {
                // Collapse the previously expanded item
                val prevExpandedPosition = expandedPosition
                expandedPosition = position
                notifyItemChanged(prevExpandedPosition)
                notifyItemChanged(expandedPosition)
            } else {
                expandedPosition = -1
                notifyItemChanged(position)
            }
        }

        holder.customerNameView2.setOnClickListener {
            if (expandedPosition == holder.adapterPosition) {
                // Open a new fragment here
                Log.d("neel" , "Customer---->")
                val action = CustomerListDirections.actionCustomerListToCustomerDetails(item.toString() , "123")
                val navController = Navigation.findNavController(holder.itemView)
                navController.navigate(action)
            }
        }


    }

    override fun getItemCount(): Int {
        return customerNameList.size
    }
}
