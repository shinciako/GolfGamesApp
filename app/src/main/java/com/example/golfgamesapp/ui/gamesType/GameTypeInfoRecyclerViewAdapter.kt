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
) : RecyclerView.Adapter<GameTypeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameTypeHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.game_types, parent, false)
        return GameTypeHolder(listItem)
    }

    override fun onBindViewHolder(holder: GameTypeHolder, position: Int) {
        val game = gameTypeList[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int {
        return gameTypeList.size
    }
}

class GameTypeHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(gameType: GameType) {
        val tvGameType = view.findViewById<TextView>(R.id.tvGames)
        tvGameType.text = gameType.name

        view.setOnClickListener {
            it.findNavController()
                .navigate(GameTypeFragmentDirections.actionNavigationDashboardToShortGames(gameType))
        }
    }
}
