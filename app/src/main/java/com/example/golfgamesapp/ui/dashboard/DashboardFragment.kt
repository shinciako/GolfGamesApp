package com.example.golfgamesapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    val gamesList = listOf<Game>(
        Game("Around the green"),
        Game("Approaches"),
        Game("Long game")
    )
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val rvGames = binding.myRecyclerView
        rvGames.layoutManager = LinearLayoutManager(activity)
        rvGames.adapter = MyRecyclerViewAdapter(
            gamesList,
        ) { selectedItem: Game ->
            listGameClicked(selectedItem)
        }


        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listGameClicked(game: Game){
        Toast.makeText(
            this@DashboardFragment.context,
            "Selected gameFragment : ${game.name}",
            Toast.LENGTH_LONG
        ).show()
    }
}