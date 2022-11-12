package com.example.golfgamesapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.golfgamesapp.databinding.FragmentVideoGameBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer


class VideoGameFragment : Fragment() {

    private var isFullScreen = false
    private var _binding: FragmentVideoGameBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: VideoGameFragmentArgs by navArgs()
    private lateinit var simpleExoPlayer:SimpleExoPlayer
    private lateinit var btnFullScreen: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).hideSystemBars()
        _binding = FragmentVideoGameBinding.inflate(inflater, container, false)
        val input = navigationArgs.gameInfo
        (activity as MainActivity).setActionBarTitle(input.name)
        setupExoPlayer()
        return binding.root
    }


    //Stopping video
    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showSystemBars()
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            (activity as MainActivity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        simpleExoPlayer.stop()
    }


    @SuppressLint("UseRequireInsteadOfGet")
    private fun setupExoPlayer(){
        btnFullScreen = binding.root.findViewById(R.id.bt_fullscreen)


        btnFullScreen.setOnClickListener{
            if(!isFullScreen){
                btnFullScreen.setImageDrawable(this.context?.let { it1 ->
                    ContextCompat.getDrawable(
                        it1, R.drawable.ic_baseline_fullscreen_exit)
                })
                (activity as MainActivity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            else{
                btnFullScreen.setImageDrawable(this.context?.let { it1 ->
                    ContextCompat.getDrawable(
                        it1, R.drawable.ic_baseline_fullscreen)
                })
                (activity as MainActivity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            isFullScreen = !isFullScreen
        }


        simpleExoPlayer = this.context?.let {
            SimpleExoPlayer.Builder(it)
                .setSeekBackIncrementMs(5000)
                .setSeekForwardIncrementMs(5000)
                .build()
        }!!
        binding.player.player = simpleExoPlayer
        binding.player.keepScreenOn = true
        simpleExoPlayer.addListener(object: Player.Listener{
            @Deprecated("Deprecated in Java")
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if(playbackState == Player.STATE_BUFFERING){
                    binding.progressBar.visibility = View.VISIBLE
                } else if(playbackState == Player.STATE_READY)
                    binding.progressBar.visibility = View.GONE
            }
        })
        val videoSource = Uri.parse("android.resource://"+activity?.packageName+"/"+R.raw.par18)
        val mediaItem = MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}