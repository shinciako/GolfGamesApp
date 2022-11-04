package com.example.golfgamesapp.ui.gamesType.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.MainActivity
import com.example.golfgamesapp.databinding.FragmentGamesBinding
import com.example.golfgamesapp.ui.gamesType.GameType


class Games : Fragment() {
    private var _binding: FragmentGamesBinding? = null
    private lateinit var input: GameType
    private val navigationArgs : GamesArgs by navArgs()
    private var shortGamesList = listOf(
        Game("21 game",0,0),
        Game("Chipping zone challenge",1,0),
        Game("Find flag",2,1)
    )

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)

        input = navigationArgs.game
        (activity as MainActivity).setActionBarTitle(input.name)
        shortGamesList = shortGamesList.filter { it.gameType == input.type }
        val shortGames = binding.recyclerViewShortGames
        shortGames.layoutManager = LinearLayoutManager(activity)
        shortGames.adapter = ShortGamesRecyclerViewAdapter(
            shortGamesList,
        ) { selectedItem: Game ->
            listGameClicked(selectedItem)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
    private fun listGameClicked(shortGame: Game){
        Toast.makeText(
            this@Games.context,
            "Selected gameFragment : ${shortGame.name}",
            Toast.LENGTH_LONG
        ).show()
    }
}