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
import com.example.golfgamesapp.ui.gamesType.GameType
import com.example.golfgamesapp.ui.gamesType.Type


class GameInfoFragment : Fragment() {
    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs : GameInfoFragmentArgs by navArgs()
    private lateinit var input: GameType
    private var gameInfoList = listOf(
        GameInfo("Par 18", Type.Short,"You play 9 holes from around the green. " +
                "The idea in this game is to make up and down from every ball. " +
                "See how many shots you need to complete 9 holes."),
        GameInfo("Bank",Type.Short, "Select 6 pins and you have 30 feet in the " +
                "bank. Your first shot goes to 5 feet, then you have 25 feet left in the bank." +
                " The pressure builds as your near the end and only have few feet left " +
                "in the bank."),
        GameInfo("Consecutive greens",Type.Mid, "This is a simple game. " +
                "Pick one green and hit shots onto this green consecutively until you miss the" +
                " green. It is way better than hitting balls to the target without any pressure."),
        GameInfo("9 shots",Type.Mid, "Pick a target on the driving range. " +
                "Try to hit nine shots toward the target:\n" +
                "Draw\n Fade\n Straight\n High Draw\n High Fade\n High Straight\n" +
                "Low Draw\n Low Fade\n Low Straight\n"),
        GameInfo("Consecutive fairways",Type.Long, "Pick a fairway on the " +
                "driving range and hit shots onto this green consecutively " +
                "until you miss the green."),
        GameInfo("Longest drive", Type.Long, "Using launch monitor, record your " +
                "longest drive of the session")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        input = navigationArgs.gameType
        (activity as MainActivity).setActionBarTitle(input.name)
        setupRv()
        return binding.root
    }

    private fun setupRv(){
        gameInfoList = gameInfoList.filter { it.gameType == input.type }
        val shortGames = binding.gameInfoRecyclerView
        shortGames.layoutManager = LinearLayoutManager(activity)
        shortGames.adapter = GameInfoRecyclerViewAdapter(gameInfoList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}