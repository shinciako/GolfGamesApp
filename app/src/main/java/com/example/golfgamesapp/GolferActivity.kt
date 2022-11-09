package com.example.golfgamesapp

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.example.golfgamesapp.databinding.ActivityGolferBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

class GolferActivity : AppCompatActivity() {
    private lateinit var sf: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private lateinit var binding: ActivityGolferBinding

    companion object {
        const val IMAGE_REQUEST_CODE = 1_000;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGolferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()

        loadLastImage(binding.ivAvatarGolfer)
        var hcp = intent.getStringExtra("HCP")
        hcp = hcp?.let { converseText(it) }
        binding.etHcp.setText(hcp)

        binding.btnApply.setOnClickListener(){
            hcp = binding.etHcp.text.toString()
            if(validateHcp(hcp!!)){
                val hcpFloat = (hcp!!.toFloat() * 10.0).roundToInt() / 10.0
                val outPut = "hcp: $hcpFloat"
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("HCP", "$outPut")
                Log.i("Golfer", "$hcp")
                this.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
            CoroutineScope(Dispatchers.IO).launch{
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

    private fun saveImage(imageView: ImageView) {
        val baos = ByteArrayOutputStream()
        val bitmap = imageView.drawable.toBitmap()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)

        editor.apply(){
            putString("encodedImage", encodedImage)
            apply()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            binding.ivAvatarGolfer.setImageURI(data?.data)
        }
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