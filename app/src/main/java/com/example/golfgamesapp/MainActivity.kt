package com.example.golfgamesapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.golfgamesapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sf: SharedPreferences
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        controlNavVisibility(navController)
    }


    //Function to control visibility in fragments
    private fun controlNavVisibility(navController: NavController){
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.games ||
                destination.id == R.id.chosenGame
            )
                navView.visibility = View.GONE
            else navView.visibility = View.VISIBLE
        }
    }

    //Function for sending data from activity to another activity
    fun sendHcp(tvHcp: TextView){
        val intent = Intent(this, GolferActivity::class.java)
        intent.putExtra("HCP", tvHcp.text.toString())
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //Function for receiving data from activity to another activity
    fun receiveHcp(): String? {
        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        val newHcp = intent.getStringExtra("HCP")
        if(newHcp==null) {
            return sf.getString("sf_hcp", null)
        }
        sf.edit().apply{
            putString("sf_hcp", newHcp)
            commit()
        }
        return newHcp
    }

    //Changing title bar text
    fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }

    //enabling back button in fragments
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment_activity_main))
                || super.onOptionsItemSelected(item)
    }
}