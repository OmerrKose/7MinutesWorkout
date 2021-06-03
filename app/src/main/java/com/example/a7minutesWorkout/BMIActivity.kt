package com.example.a7minutesWorkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import com.example.a7minutesworkout.R
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        setSupportActionBar(findViewById(R.id.toolbarBMIActivity))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"

        findViewById<Toolbar>(R.id.toolbarBMIActivity).setNavigationOnClickListener {
            onBackPressed()
        }

        // CALCULATE BUTTON
        findViewById<Button>(R.id.buttonCalculateUnits).setOnClickListener {
            if (validateMetricUnits()) {
                val heightValue: Float =
                    findViewById<AppCompatEditText>(R.id.editTextMetricUnitHeight).text.toString()
                        .toFloat() / 100
                val weightValue: Float =
                    findViewById<AppCompatEditText>(R.id.editTextMetricUnitWeight).text.toString()
                        .toFloat()

                val bmi = weightValue / (heightValue * heightValue)
                displayBMIResults(bmi)
            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid values",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    // Function to compare the user inputs
    private fun displayBMIResults(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of yourself! Workout!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately Obese)"
            bmiDescription = "Oops! You really need to take care of yourself! Workout!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class | (Severely Obese)"
            bmiDescription = "Oops! You really need to take care of yourself! Workout!"
        } else {
            bmiLabel = "Obese Class | (Very Severely Obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val textViewYourBMI = findViewById<TextView>(R.id.textViewYourBMI)
        val textViewBMIType = findViewById<TextView>(R.id.textViewBMIType)
        val textViewBMIValue = findViewById<TextView>(R.id.textViewBMIValue)
        val textViewBMIDescription = findViewById<TextView>(R.id.textViewBMIDescription)

        textViewYourBMI.visibility = View.VISIBLE
        textViewBMIValue.visibility = View.VISIBLE
        textViewBMIType.visibility = View.VISIBLE
        textViewBMIDescription.visibility = View.VISIBLE

        BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
            .also { textViewBMIValue.text = it }

        textViewBMIType.text = bmiLabel
        textViewBMIDescription.text = bmiDescription
    }


    // Function to check if user inputs exist
    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (findViewById<AppCompatEditText>(R.id.editTextMetricUnitWeight).text.toString()
                .isEmpty()
        )
            isValid = false
        else if (findViewById<AppCompatEditText>(R.id.editTextMetricUnitHeight).text.toString()
                .isEmpty()
        )
            isValid = false

        return isValid
    }
}