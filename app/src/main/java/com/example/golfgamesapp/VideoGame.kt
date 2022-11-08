package com.example.golfgamesapp

import android.net.Uri
import android.net.Uri.parse
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.golfgamesapp.databinding.FragmentVideoGameBinding


class VideoGame : Fragment() {
    private var _binding: FragmentVideoGameBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: VideoGameArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoGameBinding.inflate(inflater, container, false)
        var input = navigationArgs.videoGame
        (activity as MainActivity).setActionBarTitle(input.name)
        setupVv(binding.vvIntroduction)
        return binding.root
    }

    private fun setupVv(videoView: VideoView){
        val mediaController = MediaController(this.context)
        mediaController.setAnchorView(videoView)
        val uri : Uri = parse("android.resource://"+activity?.packageName+"/"+R.raw.par18)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()

    }
}