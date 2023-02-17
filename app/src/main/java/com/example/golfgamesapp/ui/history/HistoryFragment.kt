package com.example.golfgamesapp.ui.history

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.MainActivity
import com.example.golfgamesapp.databinding.FragmentHistoryBinding
import com.example.golfgamesapp.db.Game
import com.example.golfgamesapp.db.GameDatabase
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*


class HistoryFragment : Fragment() {
    private lateinit var adapter: HistoryGameRecyclerViewAdapter
    private lateinit var gamesList: List<Game>

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var cal = Calendar.getInstance()
    private var dateFrom: OffsetDateTime = OffsetDateTime.MIN
    private var dateTo: OffsetDateTime = OffsetDateTime.MAX.minusDays(1)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setupDb()
        setupRv()
        createOnDateSetListener()
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupDb() {
        val dao = GameDatabase.getInstance((activity as MainActivity).application).gameDao()
        dao.getAllGames().observe(viewLifecycleOwner) {
            adapter.setList(it)
            gamesList = it
        }
    }

    private fun setupRv() {
        val rvGames = binding.rvGames
        rvGames.layoutManager = LinearLayoutManager(this.context)
        adapter = HistoryGameRecyclerViewAdapter()
        rvGames.adapter = adapter
    }

    private fun createOnDateSetListener() {
        val dateSetListenerFrom =
            OnDateSetListener { _, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                updateDateInView(cal.time, binding.tvDatePickFromDate)
                dateFrom = cal.time.toInstant().atOffset(ZoneOffset.UTC)
                val filteredData =
                    filterByDate(gamesList)
                adapter.setList(filteredData)
            }

        val dateSetListenerTo =
            OnDateSetListener { _, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                updateDateInView(cal.time, binding.tvDatePickToDate)
                dateTo = cal.time.toInstant().atOffset(ZoneOffset.UTC)
                val filteredData = filterByDate(gamesList)
                adapter.setList(filteredData)
            }

        binding.tvDatePickFromDate.setOnClickListener {
            takeDate(dateSetListenerFrom)
        }

        binding.tvDatePickToDate.setOnClickListener {
            takeDate(dateSetListenerTo)
        }
    }


    private fun updateDateInView(date: Date, textView: TextView) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        textView.text = sdf.format(date)
    }

    private fun filterByDate(data: List<Game>): List<Game> {
        return data.filter { it.date >= dateFrom && it.date <= dateTo.plusDays(1) }
    }

    private fun takeDate(dateSetListener: OnDateSetListener) {
        DatePickerDialog(
            requireActivity(),
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}

