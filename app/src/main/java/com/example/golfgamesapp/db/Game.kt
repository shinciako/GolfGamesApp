package com.example.golfgamesapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "game_data_table")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    var id: Int,
    @ColumnInfo(name = "game_name")
    var name:String,
    @ColumnInfo(name = "game_points")
    var points: Int,
    @ColumnInfo(name = "date", defaultValue = "")
    var date: String
)