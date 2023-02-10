package com.example.golfgamesapp.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.golfgamesapp.R
import com.example.golfgamesapp.db.Game

class HistoryGameRecyclerViewAdapter(
) : RecyclerView.Adapter<HistoryGameHolder>() {
    var gameList = ArrayList<Game>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryGameHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listGames = layoutInflater.inflate(R.layout.list_games, parent, false)
        return HistoryGameHolder(listGames)
    }

    override fun onBindViewHolder(holder: HistoryGameHolder, position: Int) {
        holder.bind(gameList[position])
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    fun setList(games: List<Game>) {
        gameList.clear()
        gameList.addAll(games)
        notifyDataSetChanged()
    }
}


class HistoryGameHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(game: Game) {
        val tvGameName = view.findViewById<TextView>(R.id.tvGameName)
        val tvPoints = view.findViewById<TextView>(R.id.tvPoints)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        val date = "${game.date.dayOfMonth} / ${game.date.month} / ${game.date.year}"
        tvGameName.text = game.name
        tvPoints.text = game.points.toString()
        tvDate.text = date
    }
}