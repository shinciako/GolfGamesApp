package com.example.golfgamesapp.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Game::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to=2)]
)
abstract class GameDatabase : RoomDatabase(){

    abstract fun gameDao(): GameDao
    companion object{
        @Volatile
        private var INSTANCE : GameDatabase? = null
        fun getInstance(context: Context):GameDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "game_data_database"
                    ).build()
                }
                //context.deleteDatabase("game_data_database");
                return instance
            }
        }
    }

}