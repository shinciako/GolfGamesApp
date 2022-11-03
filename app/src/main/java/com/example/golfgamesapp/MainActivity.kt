package com.example.golfgamesapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.golfgamesapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sf: SharedPreferences
    private lateinit var newHcp:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//        supportActionBar?.hide()
        newHcp = intent.getStringExtra("HCP").toString()
        Log.i("HCP","UPDATEDMAIN $newHcp")
    }


    //Function for sending data from activity to another activity
    fun sendHcp(tvHcp: TextView){
        val intent = Intent(this, GolferActivity::class.java)
        intent.putExtra("HCP", tvHcp.text.toString())
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //Functions for receiving data from activity to another activity
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
}