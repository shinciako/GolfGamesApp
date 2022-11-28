package com.example.golfgamesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Game::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class GameDatabase : RoomDatabase(){

    abstract fun gameDao(): GameDao
    companion object{
        @Volatile
        private var INSTANCE : GameDatabase? = null
        fun getInstance(context: Context):GameDatabase{
            synchronized(this){
//                context.deleteDatabase("game_data_database")
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "game_data_database"
                    ).build()
                }
                return instance
            }
        }
    }

}