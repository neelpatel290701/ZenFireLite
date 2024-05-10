package com.example.zenfirelite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.SettingUsersModel

class AdapterForSettingUsers(private val userList : List<SettingUsersModel>) :
    RecyclerView.Adapter<AdapterForSettingUsers.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName : TextView = view.findViewById(R.id.userName)
        val userEmail : TextView = view.findViewById(R.id.userEmail)
        val userContactNum : TextView = view.findViewById(R.id.userContactNum)
        val userRole : TextView = view.findViewById(R.id.userRole)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.setting_users_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = userList[position]

        holder.userName.text = ItemsViewModel.userName
        holder.userEmail.text = ItemsViewModel.email
        holder.userContactNum.text = ItemsViewModel.contactNumber
        holder.userRole.text = ItemsViewModel.role
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    fun truncateText(value : String) : String {
        if (value.length > 10) {
            val truncatedText = value.substring(0, 10) + "..."
            return truncatedText
        }

        return value
    }

}