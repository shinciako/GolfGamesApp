package com.example.golfgamesapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.golfgamesapp.databinding.ActivityGolferBinding
import kotlin.math.roundToInt

class GolferActivity : AppCompatActivity() {
    private lateinit var sf: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private lateinit var binding: ActivityGolferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGolferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()

        var hcp = intent.getStringExtra("HCP")
        hcp = hcp?.let { converseText(it) }
        binding.etHcp.setText(hcp)

        binding.btnApply.setOnClickListener(){
            hcp = binding.etHcp.text.toString()
            if(validateHcp(hcp!!)){
                val hcpFloat = (hcp!!.toFloat() * 10.0).roundToInt() / 10.0
                val outPut = "hcp: $hcpFloat"
                editor.apply{
                    putString("sf_hcp", outPut)
                    commit()
                }
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("HCP", "$outPut")
                Log.i("Golfer", "$hcp")
                this.startActivity(intent)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val hcpPause = binding.etHcp.text.toString()
        editor.apply {
            putString("sf_hcpP",hcpPause)
            commit()
        }

    }

    override fun onResume() {
        super.onResume()
        val hcpPause = sf.getString("sf_hcpP",null)
        binding.etHcp.setText(hcpPause)
    }

    private fun converseText(text:String):String{
        return text.replace("hcp: ", "")
    }

    private fun validateHcp(hcp: String):Boolean{
        val hcpToFloat = hcp.toFloat()
        Log.i("HCP", "$hcpToFloat")
        if (hcpToFloat>54.0f || hcpToFloat<0.0f){
            Toast.makeText(
                this@GolferActivity,
                "Please, enter hcp from 0-54",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

}