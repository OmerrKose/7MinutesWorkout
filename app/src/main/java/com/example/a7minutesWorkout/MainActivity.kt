package com.example.a7minutesWorkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.example.a7minutesworkout.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start the application when pressed start
        val startButton = findViewById<LinearLayout>(R.id.mainPageStartButton)
        startButton.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        // Start the BMI when pressed BMI
        val bmiButton = findViewById<LinearLayout>(R.id.bmiButton)
        bmiButton.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }
    }
}