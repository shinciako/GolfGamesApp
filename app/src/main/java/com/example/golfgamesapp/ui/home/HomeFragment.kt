package com.example.golfgamesapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.golfgamesapp.MainActivity
import com.example.golfgamesapp.databinding.FragmentHomeBinding


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

    override fun onStart() {
        super.onStart()
        updateHcp()
        (activity as MainActivity).loadLastImage(binding.ivAvatar)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateHcp(){
        hcp = binding.tvHcp
        hcp.text = (activity as MainActivity).receiveHcp()
        binding.btnSettings.setOnClickListener{
            (activity as MainActivity).sendHcp(hcp)
            hcp.text = (activity as MainActivity).receiveHcp()
            Log.i("HCP","UPDATED ${hcp.text}")
        }
    }

}