package com.example.golfgamesapp

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