package com.example.golfgamesapp

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.databinding.FragmentGameRegisterBinding
import com.example.golfgamesapp.db.Game
import com.example.golfgamesapp.db.GameDatabase
import com.example.golfgamesapp.ui.gamesType.MyRecyclerViewAdapter
import com.example.golfgamesapp.ui.gamesType.games.GameInfo
import java.util.*


class GameRegisterFragment : Fragment() {
    private var _binding: FragmentGameRegisterBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: GameRegisterFragmentArgs by navArgs()
    private lateinit var input : GameInfo

    private lateinit var viewModel: GameViewModel
    private lateinit var adapter: GameRecyclerViewAdapter
    private var isListItemClicked = false


    private lateinit var selectedGame: Game

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameRegisterBinding.inflate(inflater, container, false)
        input = navigationArgs.gameInfo
        (activity as MainActivity).setActionBarTitle(input.name)

        val dao = GameDatabase.getInstance((activity as MainActivity).application).gameDao()
        val factory = GameViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[GameViewModel::class.java]



        binding.btnSave.setOnClickListener(){
            if(isListItemClicked){
                updateGameData()
                clearInput()
            }else{
                saveGameData()
                clearInput()
            }
        }
        binding.btnClear.setOnClickListener(){
            if(isListItemClicked){
                deleteGameData()
                clearInput()
            }
            else {
                clearInput()
            }
        }
        setupRv()
        return binding.root
    }


    private fun saveGameData(){
        val points = binding.etPoints.text.toString().toInt()
        val date = binding.etDate.text.toString()
        val game = Game(0,input.name,points, date)
        viewModel.insertGame(game)
        Toast.makeText(
            this.context,
            "You have added a new ${game.name} game",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun clearInput(){
        binding.etPoints.setText("")
        binding.etDate.setText("")
    }

    private fun updateGameData(){
        viewModel.updateGame(
            Game(
                selectedGame.id,
                selectedGame.name,
                binding.etPoints.text.toString().toInt(),
                binding.etDate.text.toString()
            )
        )
        binding.btnSave.text = "Save"
        binding.btnClear.text = "Clear"
        isListItemClicked = false
    }

    private fun deleteGameData(){
        viewModel.deleteGame(
            Game(
                selectedGame.id,
                selectedGame.name,
                selectedGame.points,
                selectedGame.date
            )
        )
        binding.btnSave.text = "Save"
        binding.btnClear.text = "Clear"
        isListItemClicked = false
    }

    private fun setupRv(){
        val rvGames = binding.rvGames
        rvGames.layoutManager = LinearLayoutManager(this.context)
        adapter = GameRecyclerViewAdapter(input.name){
            selectedItem:Game -> listItemClicked(selectedItem)
        }
        rvGames.adapter = adapter
        displayGameList()
    }

    private fun displayGameList(){
        viewModel.games.observe(viewLifecycleOwner) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun listItemClicked(game: Game){
        selectedGame = game
        binding.btnSave.text = "Update"
        binding.btnClear.text = "Delete"
        isListItemClicked = true
        binding.etPoints.setText(selectedGame.points.toString())
        binding.etDate.setText(selectedGame.date)
    }

}