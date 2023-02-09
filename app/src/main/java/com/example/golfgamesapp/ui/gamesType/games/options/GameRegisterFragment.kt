package com.example.golfgamesapp.ui.gamesType.games.options


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golfgamesapp.MainActivity
import com.example.golfgamesapp.R
import com.example.golfgamesapp.databinding.FragmentGameRegisterBinding
import com.example.golfgamesapp.db.Game
import com.example.golfgamesapp.db.GameDatabase
import com.example.golfgamesapp.ui.gamesType.games.GameInfo
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.util.*


class GameRegisterFragment : Fragment() {
    private var _binding: FragmentGameRegisterBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: GameRegisterFragmentArgs by navArgs()
    private lateinit var input: GameInfo

    private lateinit var viewModel: GameViewModel
    private lateinit var adapter: GameRecyclerViewAdapter
    private var isListItemClicked = false

    private val channelID = "com.example.golfgamesapp.channel1"
    private var notificationManager: NotificationManager? = null


    private lateinit var selectedGame: Game
    private var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameRegisterBinding.inflate(inflater, container, false)
        input = navigationArgs.gameInfo
        (activity as MainActivity).setActionBarTitle(input.name)
        setupDb()
        createOnDateSetListener()
        setupNotifications()
        setupButtons()
        setupRv()
        return binding.root
    }

    private fun createOnDateSetListener() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                updateDateInView(cal.time)
                Log.i("date", "${cal.time}")
            }
        binding.tvDatePick.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateDateInView(date: Date) {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.tvDatePick.text = sdf.format(date)
    }

    private fun setupDb() {
        val dao = GameDatabase.getInstance((activity as MainActivity).application).gameDao()
        val factory = GameViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[GameViewModel::class.java]
    }

    //Notifications related functions
    private fun setupNotifications() {
        notificationManager = requireActivity()
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "DemoChannel", "this is a demo")
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance).apply {
            description = channelDescription
        }
        notificationManager?.createNotificationChannel(channel)
    }

    private fun displayNotification(game: Game) {
        val notificationId = 45
        val notification = NotificationCompat.Builder(requireActivity(), channelID)
            .setContentTitle("Golf Games App")
            .setContentText("You scored ${game.points} in ${game.name}")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManager?.notify(notificationId, notification)

    }

    //Button related functions
    private fun setupButtons() {
        binding.btnSave.setOnClickListener {
            if (validatePoints()) {
                if (isListItemClicked) {
                    updateGameData()
                    clearInput()
                } else {
                    saveGameData()
                    clearInput()
                }
            }
        }
        binding.btnClear.setOnClickListener {
            if (isListItemClicked) {
                deleteGameData()
                clearInput()
            } else {
                clearInput()
            }
        }
    }

    private fun saveGameData() {
        val points = binding.etPoints.text.toString().toInt()
        val date = cal.time.toInstant().atOffset(ZoneOffset.UTC)
        val game = Game(0, input.name, points, date)
        viewModel.insertGame(game)
        displayNotification(game)
    }

    private fun validatePoints(): Boolean {
        if (binding.etPoints.text.isNullOrBlank()) {
            Toast.makeText(
                this.context,
                "Input must be integer",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun clearInput() {
        binding.etPoints.setText("")
        binding.tvDatePick.text = "--/--/----"
    }

    private fun updateGameData() {
        viewModel.updateGame(
            Game(
                selectedGame.id,
                selectedGame.name,
                binding.etPoints.text.toString().toInt(),
                cal.time.toInstant().atOffset(ZoneOffset.UTC)
            )
        )
        binding.btnSave.text = "Save"
        binding.btnClear.text = "Clear"
        isListItemClicked = false
    }

    private fun deleteGameData() {
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

    private fun setupRv() {
        val rvGames = binding.rvGames
        rvGames.layoutManager = LinearLayoutManager(this.context)
        adapter = GameRecyclerViewAdapter(input.name) { selectedItem: Game ->
            listItemClicked(selectedItem)
        }
        rvGames.adapter = adapter
        displayGameList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayGameList() {
        viewModel.games.observe(viewLifecycleOwner) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun listItemClicked(game: Game) {
        selectedGame = game
        binding.btnSave.text = "Update"
        binding.btnClear.text = "Delete"
        isListItemClicked = true
        binding.etPoints.setText(selectedGame.points.toString())
        updateDateInView(Date(selectedGame.date.toInstant().toEpochMilli()))
    }
}