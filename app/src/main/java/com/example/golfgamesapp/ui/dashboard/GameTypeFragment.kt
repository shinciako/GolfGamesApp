package com.example.golfgamesapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.databinding.FragmentGameTypeBinding

class GameTypeFragment : Fragment() {

    private var _binding: FragmentGameTypeBinding? = null
    val gamesList = listOf<GameType>(
        GameType("Around the green",0),
        GameType("Approaches",1),
        GameType("Long game",2)
    )
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameTypeBinding.inflate(inflater, container, false)
        val rvGames = binding.myRecyclerView
        rvGames.layoutManager = LinearLayoutManager(activity)
        rvGames.adapter = MyRecyclerViewAdapter(
            gamesList,
        ) { selectedItem: GameType ->
            listGameClicked(selectedItem)
        }
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listGameClicked(game: GameType){
        Toast.makeText(
            this@GameTypeFragment.context,
            "Selected gameFragment : ${game.name}",
            Toast.LENGTH_LONG
        ).show()
    }
}