package com.example.golfgamesapp.ui.gamesType.games.options

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.golfgamesapp.MainActivity
import com.example.golfgamesapp.R
import com.example.golfgamesapp.databinding.FragmentVideoGameBinding
import com.example.golfgamesapp.ui.gamesType.games.GameInfo
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
    private var fileName = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).hideSystemBars()
        _binding = FragmentVideoGameBinding.inflate(inflater, container, false)
        val input = navigationArgs.gameInfo
        pickVideo(input)
        (activity as MainActivity).setActionBarTitle(input.name)
        setupExoPlayer()
        return binding.root
    }


    private fun pickVideo(input: GameInfo){
        when(input.name){ // dummy videos
            "Par 18" -> fileName = R.raw.par18
            "Bank" -> fileName = R.raw.bank
            "Consecutive greens" -> fileName = R.raw.green
            "9 shots" -> fileName = R.raw.shots9
            "Consecutive fairways" -> fileName = R.raw.fairway
            "Longest drive" -> fileName = R.raw.longest
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showSystemBars()
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            (activity as MainActivity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        simpleExoPlayer.stop()
    }

    @SuppressLint("UseRequireInsteadOfGet", "SourceLockedOrientationActivity")
    private fun setupExoPlayer(){
        btnFullScreen = binding.root.findViewById(R.id.bt_fullscreen)


        btnFullScreen.setOnClickListener{
            if(!isFullScreen){
                btnFullScreen.setImageDrawable(this.context?.let { it1 ->
                    ContextCompat.getDrawable(
                        it1, R.drawable.ic_baseline_fullscreen_exit
                    )
                })
                (activity as MainActivity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            else{
                btnFullScreen.setImageDrawable(this.context?.let { it1 ->
                    ContextCompat.getDrawable(
                        it1, R.drawable.ic_baseline_fullscreen
                    )
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

        val videoSource = Uri.parse("android.resource://"+activity?.packageName+"/"+ fileName)
        val mediaItem = MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}