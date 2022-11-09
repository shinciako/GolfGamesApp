package com.example.golfgamesapp.ui.gamesType.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.MainActivity
import com.example.golfgamesapp.databinding.FragmentGamesBinding
import com.example.golfgamesapp.ui.gamesType.GameTypeInfo
import com.example.golfgamesapp.ui.gamesType.Type


class GameInfoFragment : Fragment() {
    private var _binding: FragmentGamesBinding? = null
    private val navigationArgs : GameInfoFragmentArgs by navArgs()
    private lateinit var input: GameTypeInfo
    private var shortGamesList = listOf(
        GameInfo("Par 18", Type.Short,"You play 9 holes from around the green. " +
                "The idea in this game is to make up and down from every ball. " +
                "See how many shots you need to complete 9 holes."),
        GameInfo("Bank",Type.Short, "Select 6 pins and your highest score is the" +
                " number of feet you have in the bank. For example your highest score was 30ft " +
                "in total from the hole. Your first shot goes to 5ft, then you have 25 feet " +
                "left in the bank. The pressure builds as your near the end and only have few " +
                "feet left in the bank."),
        GameInfo("Consecutive greens",Type.Mid, "This is a simple game. " +
                "Pick one green and hit shots onto this green consecutively until you miss the" +
                " green. It is way better than hitting balls to the target without any pressure.")
    )

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        input = navigationArgs.gameTypeInfo
        (activity as MainActivity).setActionBarTitle(input.name)
        setupRv()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRv(){
        shortGamesList = shortGamesList.filter { it.gameType == input.type }
        val shortGames = binding.recyclerViewShortGames
        shortGames.layoutManager = LinearLayoutManager(activity)
        shortGames.adapter = ShortGamesRecyclerViewAdapter(shortGamesList)
    }
}