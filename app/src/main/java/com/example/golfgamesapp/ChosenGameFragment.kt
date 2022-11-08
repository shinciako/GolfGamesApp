package com.example.golfgamesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.golfgamesapp.databinding.FragmentChosenGameBinding


class ChosenGameFragment : Fragment() {
    private var _binding: FragmentChosenGameBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs : ChosenGameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val input = navigationArgs.chosenGame
        _binding = FragmentChosenGameBinding.inflate(inflater, container, false)
        (activity as MainActivity).setActionBarTitle(input.name)
        binding.tvDescription.text = input.description
        binding.btnVideo.setOnClickListener{
            it.findNavController().navigate(ChosenGameFragmentDirections.actionChosenGameToVideoGame(input))
        }
        return binding.root
    }
}