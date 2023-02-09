package com.example.golfgamesapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.golfgamesapp.databinding.ActivityGolferBinding
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

class GolferActivity : AppCompatActivity() {
    private lateinit var sf: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private lateinit var binding: ActivityGolferBinding
    private lateinit var hcp: String

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            binding.ivAvatarGolfer.setImageURI(it.data?.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGolferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Settings"
        setupSf()
        loadLastImage(binding.ivAvatarGolfer)
        setupHcp()
        setupButtons()
    }

    private fun setupSf(){
        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()
    }

    private fun setupHcp(){
        hcp = intent.getStringExtra("HCP").toString()
        hcp = converseText(hcp)
        binding.etHcp.setText(hcp)
    }

    private fun setupButtons(){
        binding.btnApply.setOnClickListener {
            hcp = binding.etHcp.text.toString()
            if(validateHcp(hcp)){
                val hcpFloat = (hcp.toFloat() * 10.0).roundToInt() / 10.0
                val outPut = "hcp: $hcpFloat"
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("HCP", outPut)
                this.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                saveImage(binding.ivAvatarGolfer)
            }
        }

        binding.btnChangeAvatar.setOnClickListener{
            pickImageFromGallery()
        }
    }


    //Saving etHcp in shared preferences
    override fun onPause() {
        super.onPause()
        val hcpPause = binding.etHcp.text.toString()
        editor.apply {
            putString("sf_hcpP",hcpPause)
            commit()
        }

    }

    //Restoring etHcp from shared preferences
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
        if (hcpToFloat>54.0f || hcpToFloat<-10.0f){
            Toast.makeText(
                this@GolferActivity,
                "Please, enter hcp from -10 to 54",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun saveImage(imageView: ImageView) {
        val baos = ByteArrayOutputStream()
        val bitmap = imageView.drawable.toBitmap()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
        editor.apply {
            putString("encodedImage", encodedImage)
            apply()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        getResult.launch(intent)
    }

    private fun loadLastImage(imageView: ImageView) {
        val encodedImage = sf.getString("encodedImage", "DEFAULT")
        if (encodedImage != "DEFAULT") {
            val imageBytes = Base64.decode(encodedImage, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            imageView.setImageBitmap(decodedImage)
        }
    }
}