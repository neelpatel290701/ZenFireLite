package com.example.zenfirelite.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.ActivityMainBinding
import com.example.zenfirelite.prefs
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar : Toolbar
    private lateinit var navigationDrawerLayout :DrawerLayout
    private var isOldMenuLoaded = true

    private var lastSelectedFragmentId: Int = R.id.homeScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.toolbar
        navigationDrawerLayout = binding.drawerLayout
        navigationView = binding.navView

        setSupportActionBar(toolbar)
        //https://developer.android.com/develop/ui/views/components/appbar/setting-up

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.homeScreen,R.id.setting,R.id.customerList),
            navigationDrawerLayout
        )

        toolbar.setupWithNavController(navController, appBarConfiguration)

//        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener(this)

        navigationView.setCheckedItem(R.id.homeScreen)
        toggleNavigationDrawerItemsClickOnInspectorName()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val destinationFragmentId = when (item.itemId) {
            R.id.homeScreen -> R.id.homeScreen
            R.id.customerList -> R.id.customerList
            R.id.setting -> R.id.setting
            else -> null
        }
        // Close the drawer and return if the selected fragment is already displayed
        if (destinationFragmentId != null && destinationFragmentId == lastSelectedFragmentId) {
            navigationDrawerLayout.closeDrawer(GravityCompat.START)
            return true
        }
        // Update the last selected fragment ID
        lastSelectedFragmentId = destinationFragmentId ?: lastSelectedFragmentId

        val navOptions = NavOptions.Builder()
            .setPopUpTo(navController.graph.startDestinationId, false)
            .build()

        when (item.itemId) {
            R.id.homeScreen -> {
                navController.navigate(R.id.homeScreen, null, navOptions)
//                navigationView.setCheckedItem(R.id.homeScreen)
            }
            R.id.customerList -> {
                navController.navigate(R.id.customerList, null, navOptions)
//                navigationView.setCheckedItem(R.id.customerList)
            }
            R.id.setting -> {
                navController.navigate(R.id.setting, null, navOptions)
//                navigationView.setCheckedItem(R.id.setting)
            }
            R.id.sendfeedback -> Toast.makeText(this, "Send Feedback", Toast.LENGTH_SHORT).show()
            R.id.support -> Toast.makeText(this, "Support!", Toast.LENGTH_SHORT).show()
            R.id.version -> Toast.makeText(this, "Version", Toast.LENGTH_SHORT).show()
            R.id.logout ->{
                prefs.clear()
                // Navigate to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                // Finish the current activity
                finish()
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
            }
        }
        navigationDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun toggleNavigationDrawerItemsClickOnInspectorName() {
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
                    findViewById<LinearLayout>(R.id.navigationDrawer_bottomItems).visibility = View.VISIBLE
                    isOldMenuLoaded = true

                }
            }

        }
    }

}