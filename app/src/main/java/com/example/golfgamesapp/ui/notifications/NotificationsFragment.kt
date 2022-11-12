package com.example.golfgamesapp.ui.notifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.golfgamesapp.databinding.FragmentNotificationsBinding


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        setupButtons()
        return binding.root
    }

    private fun setupButtons(){
        binding.ivFb.setOnClickListener{
            redirect("https://www.facebook.com/suicideEnjoyer/")
        }
        binding.ivInsta.setOnClickListener{
            redirect("https://www.instagram.com/shinciako/")
        }
    }

    private fun redirect(url: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}