package com.example.golfgamesapp.ui.gamesType.games.options

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.golfgamesapp.R
import com.example.golfgamesapp.db.Game
import kotlin.properties.Delegates

private var cardColor by Delegates.notNull<Int>()
private var selectedColor: Int = Color.parseColor("#16699f")
private var cardViewList: MutableList<CardView> = ArrayList()

class GameRecyclerViewAdapter(
    private val gameName: String,
    private val clickListener: (Game) -> Unit
) : RecyclerView.Adapter<GameViewHolder>() {
    private val gameList = ArrayList<Game>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listGames = layoutInflater.inflate(R.layout.list_games, parent, false)
        cardColor = ContextCompat.getColor(layoutInflater.context, R.color.card_background)
        return GameViewHolder(listGames)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(gameList[position])
        if (!cardViewList.contains(holder.cv)) {
            cardViewList.add(holder.cv)
        }
        holder.cv.setOnClickListener {
            resetCardsColor()
            holder.cv.setCardBackgroundColor(selectedColor)
            clickListener(gameList[position])
        }
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    fun setList(games: List<Game>) {
        gameList.clear()
        val filteredList = games.filter { it.name == gameName }
        gameList.addAll(filteredList)
    }

    fun resetCardsColor(){
        for (cvs in cardViewList) {
            cvs.setCardBackgroundColor(cardColor)
        }
    }
}

class GameViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val cv: CardView = itemView.findViewById(R.id.cardView)
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