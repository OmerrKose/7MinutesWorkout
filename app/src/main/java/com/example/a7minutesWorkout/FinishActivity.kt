package com.example.a7minutesWorkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.a7minutesworkout.R
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(findViewById(R.id.finishActivityToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Back Button
        findViewById<Toolbar>(R.id.finishActivityToolbar).setNavigationOnClickListener {
            onBackPressed()
        }

        // Finish Button
        findViewById<Button>(R.id.finishButton).setOnClickListener {
            finish()
        }

        addDateToDatabase()
    }

    private fun addDateToDatabase() {
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.i("Date", "" + dateTime)

        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = simpleDateFormat.format(dateTime)

        val databaseHandler = SqliteOpenHelper(this, null)
        databaseHandler.addDate(date)
        Log.i("DATE: ", "Added")
    }
}