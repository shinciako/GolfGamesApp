package com.example.golfgamesapp.ui.gamesType.games.options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfgamesapp.db.Game
import com.example.golfgamesapp.db.GameDao
import kotlinx.coroutines.launch

class GameViewModel(private val dao: GameDao):ViewModel(){
    val games = dao.getAllGames()

    fun insertGame(game: Game)= viewModelScope.launch {
        dao.insertGame(game)
    }

    fun updateGame(game: Game)= viewModelScope.launch {
        dao.updateGame(game)
    }

    fun deleteGame(game: Game)= viewModelScope.launch {
        dao.deleteGame(game)
    }
}