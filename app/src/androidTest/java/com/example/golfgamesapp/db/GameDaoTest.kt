package com.example.golfgamesapp.db

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.OffsetDateTime

@RunWith(AndroidJUnit4::class)
class GameDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: GameDao
    private lateinit var database: GameDatabase
    private lateinit var offset: OffsetDateTime

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GameDatabase::class.java
        ).build()
        dao = database.gameDao()
        offset = OffsetDateTime.now()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertGameTest() = runBlocking{
        val listOfGames = listOf(Game(1,"Par 18", 21, offset))
        dao.insertGame(listOfGames[0])
        val allGames = dao.getAllGames().getOrAwaitValue()
        Truth.assertThat(allGames).isEqualTo(listOfGames)
    }

    @Test
    fun deleteGameTest() = runBlocking{
        val listOfGames = listOf(Game(1,"Par 18", 21, offset))
        dao.insertGame(listOfGames[0])
        dao.deleteGame(listOfGames[0])
        val allGames = dao.getAllGames().getOrAwaitValue()
        Truth.assertThat(allGames).isNotEqualTo(listOfGames)
    }

    @Test
    fun updateGameTest() = runBlocking{
        val listOfGames = listOf(Game(1,"Par 18", 21, offset))
        Log.i("Offset", listOfGames[0].date.toString())
        dao.insertGame(listOfGames[0])
        dao.updateGame(Game(1,"Par 18", 21, OffsetDateTime.now()))
        val allGames = dao.getAllGames().getOrAwaitValue()
        Truth.assertThat(allGames).isNotEqualTo(listOfGames)
    }
}