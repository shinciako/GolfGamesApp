package com.example.golfgamesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Update
    suspend fun updateGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("SELECT * FROM game_data_table")
    fun getAllGames(): LiveData<List<Game>>
}