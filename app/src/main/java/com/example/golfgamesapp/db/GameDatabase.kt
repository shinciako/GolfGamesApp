package com.example.golfgamesapp.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Game::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class GameDatabase : RoomDatabase(){

    abstract fun gameDao(): GameDao

    companion object{
        @Volatile
        private var INSTANCE : GameDatabase? = null

        fun getInstance(context: Context):GameDatabase{
//            context.deleteDatabase("game_data_database")
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_data_database"
                ).build()
                Log.i("isDB", "GooD")
                INSTANCE = instance
                return instance
            }
        }
    }
}