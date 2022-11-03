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
        val root: View = binding.root
//        removing upper bar with notifications
//        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hcp = binding.tvHcp
        hcp.setText((activity as MainActivity).receiveHcp())
        binding.btnSettings.setOnClickListener{
            (activity as MainActivity).sendHcp(hcp)
            hcp.text = (activity as MainActivity).receiveHcp()
            Log.i("HCP","UPDATED ${hcp.text}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}