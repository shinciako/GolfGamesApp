package com.example.golfgamesapp.ui.gamesType.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.MainActivity
import com.example.golfgamesapp.databinding.FragmentGamesBinding


class Games : Fragment() {
    private var _binding: FragmentGamesBinding? = null
    private var shortGamesList = listOf<Game>(
        Game("21 game",0,0),
        Game("Chipping zone challenge",1,0),
        Game("Find flag",2,1)
    )

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        var input = arguments?.getInt("gameType")
        var inputTitle = arguments?.getString("gameName")
        (activity as MainActivity)?.setActionBarTitle(inputTitle)

        shortGamesList = shortGamesList.filter {it.gameType==input}
        val shortGames = binding.recyclerViewShortGames
        shortGames.layoutManager = LinearLayoutManager(activity)
        shortGames.adapter = ShortGamesRecyclerViewAdapter(
            shortGamesList,
        ) { selectedItem: Game ->
            listGameClicked(selectedItem)
        }

        // Inflate the layout for this fragment
        val root: View = binding.root
        return root
    }
    private fun listGameClicked(shortGame: Game){
        Toast.makeText(
            this@Games.context,
            "Selected gameFragment : ${shortGame.name}",
            Toast.LENGTH_LONG
        ).show()
    }
}