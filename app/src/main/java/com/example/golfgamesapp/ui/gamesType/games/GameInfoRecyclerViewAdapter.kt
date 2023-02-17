package com.example.golfgamesapp.ui.gamesType.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.golfgamesapp.R

class GameInfoRecyclerViewAdapter(
    private val gameInfoList: List<GameInfo>
) : RecyclerView.Adapter<GameInfoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameInfoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.game_types, parent, false)
        return GameInfoHolder(listItem)
    }

    override fun onBindViewHolder(holder: GameInfoHolder, position: Int) {
        val gameInfo = gameInfoList[position]
        holder.bind(gameInfo)
    }

    override fun getItemCount(): Int {
        return gameInfoList.size
    }
}


class GameInfoHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(gameInfo: GameInfo) {
        val tvGameInfo = view.findViewById<TextView>(R.id.tvGames)
        tvGameInfo.text = gameInfo.name

        view.setOnClickListener {
            it.findNavController()
                .navigate(GameInfoFragmentDirections.actionGameInfoToChosenGame(gameInfo))
        }
    }


}