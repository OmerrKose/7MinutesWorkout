package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast


class ExerciseActivity : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(findViewById(R.id.exerciseActivityToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.exerciseActivityToolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setRestProgressBar()
    }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        super.onDestroy()
    }

    // Progress Bar Timer
    private fun setRestProgressBar() {
        findViewById<ProgressBar>(R.id.progressBar).progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                findViewById<ProgressBar>(R.id.progressBar).progress = 10 - restProgress
                findViewById<TextView>(R.id.progressBarTimer).text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Exercise starts...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    private fun setUpRestView() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }
}