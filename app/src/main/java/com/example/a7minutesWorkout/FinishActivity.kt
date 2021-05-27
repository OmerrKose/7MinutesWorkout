package com.example.a7minutesWorkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.a7minutesworkout.R

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
    }
}