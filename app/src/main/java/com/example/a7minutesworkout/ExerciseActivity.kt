package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class ExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(findViewById(R.id.exerciseActivityToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.exerciseActivityToolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}