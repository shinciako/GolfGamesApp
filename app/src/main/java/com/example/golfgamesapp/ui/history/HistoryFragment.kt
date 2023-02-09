package com.example.golfgamesapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.MainActivity
import com.example.golfgamesapp.databinding.FragmentHistoryBinding
import com.example.golfgamesapp.db.GameDatabase


class HistoryFragment : Fragment() {
    private lateinit var adapter: HistoryGameRecyclerViewAdapter
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setupDb()
        setupRv()
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupDb(){
        val dao = GameDatabase.getInstance((activity as MainActivity).application).gameDao()
        dao.getAllGames().observe(viewLifecycleOwner){
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupRv(){
        val rvGames = binding.rvGames
        rvGames.layoutManager = LinearLayoutManager(this.context)
        adapter = HistoryGameRecyclerViewAdapter()
        rvGames.adapter = adapter
    }
}