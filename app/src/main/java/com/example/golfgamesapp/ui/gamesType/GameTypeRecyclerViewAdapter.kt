package com.example.golfgamesapp.ui.gamesType

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.golfgamesapp.R

class MyRecyclerViewAdapter(
    private val gamesList:List<GameType>
    ) : RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.game_types, parent, false)
        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val game = gamesList[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }
}

class MyViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind (game: GameType) {
        val myTextView = view.findViewById<TextView>(R.id.tvGames)
        myTextView.text = game.name

        view.setOnClickListener {
            it.findNavController().navigate(GameTypeFragmentDirections.actionNavigationDashboardToShortGames(game))
        }
    }
}
