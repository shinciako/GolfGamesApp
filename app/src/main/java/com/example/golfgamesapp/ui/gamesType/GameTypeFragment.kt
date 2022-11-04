package com.example.golfgamesapp.ui.gamesType

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.databinding.FragmentGameTypeBinding

class GameTypeFragment : Fragment() {
    private var _binding: FragmentGameTypeBinding? = null
    private val binding get() = _binding!!

    private val gamesList = listOf(
        GameType("Around the green",0),
        GameType("Approaches",1),
        GameType("Long game",2)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameTypeBinding.inflate(inflater, container, false)
        setupRv()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRv(){
        val rvGames = binding.myRecyclerView
        rvGames.layoutManager = LinearLayoutManager(activity)
        rvGames.adapter = MyRecyclerViewAdapter(gamesList)
    }
}