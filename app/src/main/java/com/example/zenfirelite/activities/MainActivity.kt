package com.example.zenfirelite.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForCustomerList
import com.example.zenfirelite.adapters.AdapterForFireInspectorList
import com.example.zenfirelite.fragments.CustomerListDirections
import com.example.zenfirelite.interfaces.OnItemClickListenerForInspectionItem
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeScreen) ,
            findViewById<DrawerLayout>(R.id.drawer_layout)
        )

        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)


        findViewById<Toolbar>(R.id.toolbar).setOnMenuItemClickListener{ menuItem ->
            when (menuItem.itemId) {
                R.id.addCustomerOnHomepage -> {
                    OnItemClickListenerForAddCustomerOnHomePage()
                }
                else -> false
            }
        }

    }

    private fun OnItemClickListenerForAddCustomerOnHomePage() : Boolean {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.fragment_customer_list)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        val dialogWidth = WindowManager.LayoutParams.MATCH_PARENT
        val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(900, 1300)

        dialog.window?.setGravity(Gravity.CENTER)

        val customerList = ArrayList<String>()
        for (i in 1..10) {
            customerList.add("Neel Patel (1234)")
            customerList.add("Smit Patel (5678)")
            customerList.add("Kuldeep Tripathi (9012)")
            customerList.add("Dhruv Pathak (34567)")
        }

        val customerRecycleView = dialog.findViewById<RecyclerView>(R.id.customerRecycleView)
        customerRecycleView .layoutManager = LinearLayoutManager(this)
        val adapter = AdapterForCustomerList(customerList,this,true)
        customerRecycleView .adapter = adapter

        val addCustomer = dialog.findViewById<TextView>(R.id.addCustomer)

        addCustomer.setOnClickListener{
            navController.navigate(R.id.addCustomerDetails)
            dialog.dismiss()
        }

        dialog.show()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val currentFragment = navController.currentDestination?.id
        val customerMenuItem = menu?.findItem(R.id.addCustomerOnHomepage)
        Log.d("Neel", currentFragment.toString())
        // Show or hide the customer item based on the current fragment
        customerMenuItem?.isVisible = currentFragment == R.id.homeScreen

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.draweritems, menu)
        menuInflater.inflate(R.menu.toolbaritems, menu)
        return super.onCreateOptionsMenu(menu)
    }

}