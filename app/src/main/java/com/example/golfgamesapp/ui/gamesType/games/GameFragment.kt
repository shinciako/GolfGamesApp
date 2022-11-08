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


class GameFragment : Fragment() {
    private var _binding: FragmentGamesBinding? = null
    private val navigationArgs : GameFragmentArgs by navArgs()
    private var shortGamesList = listOf(
        Game("Par 18",0,0,"You play 9 holes from around the green. " +
                "The idea in this game is to make up and down from every ball. " +
                "See how many shots you need to complete 9 holes."),
        Game("Bank",1,0, "Select 6 pins and your highest score is the" +
                " number of feet you have in the bank. For example your highest score was 30ft " +
                "in total from the hole. Your first shot goes to 5ft, then you have 25 feet " +
                "left in the bank. The pressure builds as your near the end and only have few " +
                "feet left in the bank."),
        Game("Consecutive greens",2,1, "This is a simple game. " +
                "Pick one green and hit shots onto this green consecutively until you miss the" +
                " green. It is way better than hitting balls to the target without any pressure.")
    )

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)

        val input = navigationArgs.game
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
            this@GameFragment.context,
            "Selected gameFragment : ${shortGame.name}",
            Toast.LENGTH_LONG
        ).show()
    }
}