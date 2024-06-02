package com.example.zenfirelite.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
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
import com.example.zenfirelite.fragments.HomeScreen
import com.example.zenfirelite.interfaces.OnItemClickListenerForInspectionItem
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var isOldMenuLoaded = true
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        //https://developer.android.com/develop/ui/views/components/appbar/setting-up

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeScreen),
            findViewById<DrawerLayout>(R.id.drawer_layout)
        )

        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
        navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setupWithNavController(navController)


        val inspectorName: TextView = findViewById(R.id.inspectorName)
        inspectorName.setOnClickListener {
            if (isOldMenuLoaded) {
                // Load new header and menu items
                navigationView.removeHeaderView(navigationView.getHeaderView(0))
                navigationView.inflateHeaderView(R.layout.new_drawerheader)
                navigationView.menu.clear()
                navigationView.inflateMenu(R.menu.new_draweritems)
                // Remove bottom item layout
                findViewById<LinearLayout>(R.id.navigationDrawer_bottomItems).visibility = View.GONE

                isOldMenuLoaded = false
            }

            val inspectorNameAtHeader: TextView = findViewById(R.id.inspectorAtHeader)
            inspectorNameAtHeader.setOnClickListener {
                if (!isOldMenuLoaded) {
                    // Load old header and menu items
                    navigationView.removeHeaderView(navigationView.getHeaderView(0))
                    navigationView.inflateHeaderView(R.layout.drawer_header)
                    navigationView.menu.clear()
                    navigationView.inflateMenu(R.menu.draweritems)

                    // Show bottom item layout
                    findViewById<LinearLayout>(R.id.navigationDrawer_bottomItems).visibility =
                        View.VISIBLE
                    isOldMenuLoaded = true

                }
            }

        }
    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.sendfeedback -> {
                Log.d("neel","sendfeedbacl")
                true
            }
            R.id.logout -> {
                Log.d("neel","logout")
                true
            }
            else -> item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.draweritems, menu)
        menuInflater.inflate(R.menu.new_draweritems, menu)
        Log.d("neel","111111111")
        return super.onCreateOptionsMenu(menu)
    }


}