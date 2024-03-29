package com.example.golfgamesapp.ui.gamesType.games.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.golfgamesapp.MainActivity
import com.example.golfgamesapp.databinding.FragmentChosenGameBinding
import com.example.golfgamesapp.ui.gamesType.games.GameInfo


class ChosenGameFragment : Fragment() {
    private var _binding: FragmentChosenGameBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs : ChosenGameFragmentArgs by navArgs()
    private lateinit var input: GameInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        input = navigationArgs.gameInfo
        _binding = FragmentChosenGameBinding.inflate(inflater, container, false)
        binding.tvDescription.text = input.description
        (activity as MainActivity).setActionBarTitle(input.name)
        setupButtons()
        return binding.root
    }

    private fun setupButtons(){
        binding.btnPlay.setOnClickListener {
            it.findNavController().navigate(
                ChosenGameFragmentDirections.actionChosenGameToGameRegisterFragment(input)
            )
        }
        binding.btnVideo.setOnClickListener{
            it.findNavController().navigate(
                ChosenGameFragmentDirections.actionChosenGameToVideoGame(input)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}