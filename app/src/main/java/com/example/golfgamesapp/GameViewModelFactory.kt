package com.example.golfgamesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.golfgamesapp.db.GameDao

class GameViewModelFactory(private val dao: GameDao):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameViewModel::class.java)){
            return GameViewModel(dao) as T
        }
        throw IllegalAccessException("Unknown View Model Class")
    }
}