package com.example.golfgamesapp.ui.gamesType

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.golfgamesapp.R

class GameTypeViewAdapter(
    private val gameTypeList: List<GameType>
) : RecyclerView.Adapter<GameTypeInfoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameTypeInfoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.game_types, parent, false)
        return GameTypeInfoHolder(listItem)
    }

    override fun onBindViewHolder(holder: GameTypeInfoHolder, position: Int) {
        val game = gameTypeList[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int {
        return gameTypeList.size
    }
}

class GameTypeInfoHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(game: GameType) {
        val myTextView = view.findViewById<TextView>(R.id.tvGames)
        myTextView.text = game.name

        view.setOnClickListener {
            it.findNavController()
                .navigate(GameTypeFragmentDirections.actionNavigationDashboardToShortGames(game))
        }
    }
}
