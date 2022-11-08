package com.example.golfgamesapp.ui.gamesType.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.golfgamesapp.R
import com.example.golfgamesapp.ui.gamesType.GameTypeFragmentDirections

class ShortGamesRecyclerViewAdapter(
    private val shortGamesList: List<Game>,
    private val clickListener: (Game) -> Unit
    ) : RecyclerView.Adapter<ShortGameHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortGameHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.short_games, parent, false)
        return ShortGameHolder(listItem)
    }

    override fun onBindViewHolder(holder: ShortGameHolder, position: Int) {
        val shortGame = shortGamesList[position]
        holder.bind(shortGame, clickListener)
    }

    override fun getItemCount(): Int {
        return shortGamesList.size
    }
}


class ShortGameHolder(val view: View):RecyclerView.ViewHolder(view){
    fun bind(game: Game, clickListener: (Game)->Unit){
        val textViewShortGame = view.findViewById<TextView>(R.id.tvShortGames)
        textViewShortGame.text = game.name

        view.setOnClickListener(){
            clickListener(game)

            it.findNavController().navigate(GameFragmentDirections.actionGamesToChosenGame(game))
        }
    }


}