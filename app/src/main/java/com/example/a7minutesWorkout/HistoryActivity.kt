package com.example.a7minutesWorkout

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.R

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Set the toolbar
        setSupportActionBar(findViewById(R.id.toolbarHistoryActivity))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "HISTORY"

        findViewById<Toolbar>(R.id.toolbarHistoryActivity).setNavigationOnClickListener {
            onBackPressed()
        }

        // Change the color of the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimary)
        }

        getAllCompletedDates()
    }
    /*
    * This function creates the recycler view with the database that was created,
    * if there is any data in the database set recycler view visible,
    * form the adapter with the list and display the recycler view
    */
    private fun getAllCompletedDates() {
        val databaseHelper = SqliteOpenHelper(this, null)
        val allCompletedDatesList =
            databaseHelper.getAllCompleteDatesList() // Created list will be in type String

        if(allCompletedDatesList.size > 0) {
            val recyclerViewHistory = findViewById<RecyclerView>(R.id.recyclerViewHistory)
            val historyAdapter = HistoryAdapter(this, allCompletedDatesList)

            findViewById<TextView>(R.id.textViewHistory).visibility = View.VISIBLE
            recyclerViewHistory.visibility = View.VISIBLE
            findViewById<TextView>(R.id.textViewNoDate).visibility = View.GONE

            recyclerViewHistory.layoutManager = LinearLayoutManager(this)
            recyclerViewHistory.adapter = historyAdapter
        } else {
            findViewById<TextView>(R.id.textViewHistory).visibility = View.GONE
            findViewById<RecyclerView>(R.id.recyclerViewHistory).visibility = View.GONE
            findViewById<TextView>(R.id.textViewNoDate).visibility = View.VISIBLE
        }
    }
}