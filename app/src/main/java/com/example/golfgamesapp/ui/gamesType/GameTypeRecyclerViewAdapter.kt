package com.example.golfgamesapp.ui.gamesType

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.golfgamesapp.R

class MyRecyclerViewAdapter(
    private val gamesList:List<GameType>,
    private val clickListener: (GameType)->Unit
    ) : RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item, parent, false)
        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val game = gamesList[position]
        holder.bind(game, clickListener)
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }
}

class MyViewHolder(val view: View):RecyclerView.ViewHolder(view){
    fun bind (game: GameType, clickListener: (GameType)->Unit) {
        val myTextView = view.findViewById<TextView>(R.id.tvGames)
        myTextView.text = game.name

        view.setOnClickListener(){
            clickListener(game)
            val bundle = bundleOf("gameType" to game.type, "gameName" to game.name)
            it.findNavController().navigate(R.id.action_navigation_dashboard_to_shortGames, bundle)
        }
    }
}
