package com.example.golfgamesapp.ui.home

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.golfgamesapp.MainActivity
import com.example.golfgamesapp.databinding.FragmentHomeBinding
import java.io.ByteArrayOutputStream


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var hcp: TextView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateHcp()
        (activity as MainActivity).loadLastImage(binding.ivAvatar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateHcp(){
        hcp = binding.tvHcp
        hcp.setText((activity as MainActivity).receiveHcp())
        binding.btnSettings.setOnClickListener{
            (activity as MainActivity).sendHcp(hcp)
            hcp.text = (activity as MainActivity).receiveHcp()
            Log.i("HCP","UPDATED ${hcp.text}")
        }
    }

}