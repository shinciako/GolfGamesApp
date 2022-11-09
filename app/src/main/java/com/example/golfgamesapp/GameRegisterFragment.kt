package com.example.golfgamesapp

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.databinding.FragmentGameRegisterBinding
import com.example.golfgamesapp.db.Game
import com.example.golfgamesapp.db.GameDatabase
import com.example.golfgamesapp.ui.gamesType.MyRecyclerViewAdapter
import com.example.golfgamesapp.ui.gamesType.games.GameInfo


class GameRegisterFragment : Fragment() {
    private var _binding: FragmentGameRegisterBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: GameRegisterFragmentArgs by navArgs()
    private lateinit var input : GameInfo

    private lateinit var viewModel: GameViewModel
    private lateinit var adapter: GameRecyclerViewAdapter

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
            saveGameData()
            clearInput()
        }
        binding.btnClear.setOnClickListener(){
            clearInput()
        }
        setupRv()
        return binding.root
    }

    private fun saveGameData(){
        val points = binding.etPoints.text.toString().toInt()
        val game = Game(0,input.name,points)
        viewModel.insertGame(game)
    }

    private fun clearInput(){
        binding.etPoints.setText("")
    }

    private fun setupRv(){
        val rvGames = binding.rvGames
        rvGames.layoutManager = LinearLayoutManager(this@GameRegisterFragment.context)
        adapter = GameRecyclerViewAdapter()
        rvGames.adapter = adapter
        displayGameList()
    }

    private fun displayGameList(){
        viewModel.games.observe(viewLifecycleOwner) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }
}