package com.example.golfgamesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.golfgamesapp.databinding.FragmentChosenGameBinding


class ChosenGame : Fragment() {
    private var _binding: FragmentChosenGameBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs : ChosenGameArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var input = navigationArgs.chosenGame
        _binding = FragmentChosenGameBinding.inflate(inflater, container, false)
        (activity as MainActivity).setActionBarTitle(input.name)
        binding.tvDescription.text = "both of your solution are working absolutely fine but to get access of bottom nav bar in my fragments I am using your solution as it is very short and simple. – \n" +
                "Digvijay\n" +
                " May 20, 2019 at 7:48"
        return binding.root
    }
}