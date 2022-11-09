package com.example.golfgamesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.golfgamesapp.db.Game

class GameRecyclerViewAdapter():RecyclerView.Adapter<GameViewHolder>() {

    private val gameList = ArrayList<Game>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listGames = layoutInflater.inflate(R.layout.list_games, parent, false)
        return GameViewHolder(listGames)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(gameList[position])
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    fun setList(games:List<Game>){
        gameList.clear()
        gameList.addAll(games)
    }

}

class GameViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(game: Game){
        val tvGameName = view.findViewById<TextView>(R.id.tvGameName)
        val tvPoints = view.findViewById<TextView>(R.id.tvPoints)
        tvGameName.text = game.name
        tvPoints.text = game.points.toString()
    }
}