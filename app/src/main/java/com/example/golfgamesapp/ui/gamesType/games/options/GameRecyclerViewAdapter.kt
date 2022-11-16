package com.example.golfgamesapp.ui.gamesType.games.options

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.golfgamesapp.R
import com.example.golfgamesapp.db.Game

class GameRecyclerViewAdapter(
    private val gameName: String,
    private val clickListener:(Game)->Unit
):RecyclerView.Adapter<GameViewHolder>() {

    private val gameList = ArrayList<Game>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listGames = layoutInflater.inflate(R.layout.list_games, parent, false)
        return GameViewHolder(listGames)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(gameList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    fun setList(games:List<Game>){
        gameList.clear()
        val filteredList = games.filter{it.name==gameName}
        gameList.addAll(filteredList)
    }

}

class GameViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(game: Game, clickListener:(Game)->Unit){
        val tvGameName = view.findViewById<TextView>(R.id.tvGameName)
        val tvPoints = view.findViewById<TextView>(R.id.tvPoints)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        val date = "${game.date.dayOfMonth} / ${game.date.month} / ${game.date.year}"
        tvGameName.text = game.name
        tvPoints.text = game.points.toString()
        tvDate.text = date

        view.setOnClickListener{
            clickListener(game)
        }
    }
}