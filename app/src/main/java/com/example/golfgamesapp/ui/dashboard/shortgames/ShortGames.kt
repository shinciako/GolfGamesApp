package com.example.golfgamesapp.ui.dashboard.shortgames

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.R
import com.example.golfgamesapp.databinding.FragmentShortGamesBinding

class ShortGames : Fragment() {
    private var _binding: FragmentShortGamesBinding? = null
    val shortGamesList = listOf<ShortGame>(
        ShortGame("21 game",0),
        ShortGame("Chipping zone challenge",1)
    )

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShortGamesBinding.inflate(inflater, container, false)
        val shortGames = binding.recyclerViewShortGames
        shortGames.setBackgroundColor(Color.YELLOW)
        shortGames.layoutManager = LinearLayoutManager(activity)
        shortGames.adapter = ShortGamesRecyclerViewAdapter(
            shortGamesList,
        ) { selectedItem: ShortGame ->
            listGameClicked(selectedItem)
        }

        // Inflate the layout for this fragment
        val root: View = binding.root
        return root
    }
    private fun listGameClicked(shortGame: ShortGame){
        Toast.makeText(
            this@ShortGames.context,
            "Selected gameFragment : ${shortGame.name}",
            Toast.LENGTH_LONG
        ).show()
    }
}