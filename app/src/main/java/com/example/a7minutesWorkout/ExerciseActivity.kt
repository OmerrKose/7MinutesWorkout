package com.example.a7minutesWorkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.R
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    // Variables for the count down timer
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    // Variables for the exercise count down timer
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30 // Change this while testing init: 30
    private var restTimerDuration: Long = 10 // Change this while testing init: 10

    // Variables to display the moves
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercise = -1

    private var txtToSpeech: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(findViewById(R.id.exerciseActivityToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.exerciseActivityToolbar)
        toolbar.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        txtToSpeech = TextToSpeech(this, this)


        // Set Up the Exercises
        exerciseList = Constants.defaultExerciseList()
        setUpRestView()

        setUpExerciseRecyclerView()
    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        // Stop text to speech if it is not closed
        if (txtToSpeech != null) {
            txtToSpeech!!.stop()
            txtToSpeech!!.shutdown()
        }

        if (player != null) {
            player!!.stop()
        }

        super.onDestroy()
    }

    // Progress Bar Timer
    private fun setRestProgressBar() {
        findViewById<ProgressBar>(R.id.progressBar).progress = restProgress

        restTimer = object : CountDownTimer(restTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                findViewById<ProgressBar>(R.id.progressBar).progress = 10 - restProgress
                findViewById<TextView>(R.id.progressBarTimer).text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                // When the timer is finished move to the next exercise
                currentExercise++

                // Arrange the list accordingly
                exerciseList!![currentExercise].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
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
                // If there is still exercise
                if (currentExercise < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercise].setIsSelected(false)
                    exerciseList!![currentExercise].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(
                        this@ExerciseActivity,
                        FinishActivity::class.java)
                    startActivity(intent)
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

        // Announce the exercise
        speakOut(exerciseList!![currentExercise].getName())

        // Prepare the Progress Bar
        setExerciseProgressBar()

        // Set Exercises
        findViewById<ImageView>(R.id.exerciseImage).setImageResource(exerciseList!![currentExercise].getImage())
        findViewById<TextView>(R.id.exerciseNameTextView).text =
            exerciseList!![currentExercise].getName()
    }

    // Function that sets up the rest view
    private fun setUpRestView() {
        // Media Player
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false // To play only once
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Move the exercise view away bring the rest view back
        findViewById<LinearLayout>(R.id.exerciseView).visibility = View.GONE
        findViewById<LinearLayout>(R.id.restViewCounter).visibility = View.VISIBLE

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        findViewById<TextView>(R.id.upComingExercise).text =
            exerciseList!![currentExercise + 1].getName()
        setRestProgressBar()
    }

    // Function for the textToSpeech
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = txtToSpeech!!.setLanguage(Locale.US)

            // Error for the text to speech
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language chosen is not supported!")
            }
        } else {
            Log.e("TTS", "Initialization is failed!")
        }
    }

    private fun speakOut(text: String) {
        txtToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    // Exercise recycler view to display the current exercise number to the user
    private fun setUpExerciseRecyclerView() {
        val exerciseStatusAdapter = findViewById<RecyclerView>(R.id.exerciseStatusRecyclerView)
        exerciseStatusAdapter.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        exerciseStatusAdapter.adapter = exerciseAdapter
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.item_custom_dialog)

        // If "YES" is chosen than finish the activity
        customDialog.findViewById<Button>(R.id.buttonYes).setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        customDialog.findViewById<Button>(R.id.buttonNo).setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }
}