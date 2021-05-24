package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*


class ExerciseActivity : AppCompatActivity() {

    // Variables for the count down timer
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    // Variables for the exercise count down timer
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30

    // Variables to display the moves
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercise = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(findViewById(R.id.exerciseActivityToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.exerciseActivityToolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Set Up the Exercises
        exerciseList = Constants.defaultExerciseList()

        setUpRestView()
    }

    override fun onDestroy() {
        if (restTimer != null) {
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
                // When the timer is finished move to the next exercise
                currentExercise++
                setUpExerciseView()
            }
        }.start()
    }

    // Exercise Bar Timer
    private fun setExerciseProgressBar() {
        findViewById<ProgressBar>(R.id.exerciseProgressBar).progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                findViewById<ProgressBar>(R.id.exerciseProgressBar).progress =
                    exerciseTimerDuration.toInt() - exerciseProgress
                findViewById<TextView>(R.id.exerciseProgressBarTimer).text =
                    (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercise < exerciseList?.size!! - 1) {
                    setUpRestView()
                } else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "You have successfully finished the 7 minutes workout!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    // Function to set up the exercises
    private fun setUpExerciseView() {
        // Move the rest view away bring the Exercise View on the screen
        findViewById<LinearLayout>(R.id.restViewCounter).visibility = View.GONE
        findViewById<LinearLayout>(R.id.exerciseView).visibility = View.VISIBLE


        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        // Set Exercises
        findViewById<ImageView>(R.id.exerciseImage).setImageResource(exerciseList!![currentExercise].getImage())
        findViewById<TextView>(R.id.exerciseNameTextView).text =
            exerciseList!![currentExercise].getName()

        // Prepare the Progress Bar
        setExerciseProgressBar()
    }

    // Function that sets up the rest view
    private fun setUpRestView() {
        // Move the exercise view away bring the rest view back
        findViewById<LinearLayout>(R.id.exerciseView).visibility = View.GONE
        findViewById<LinearLayout>(R.id.restViewCounter).visibility = View.VISIBLE


        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }
}