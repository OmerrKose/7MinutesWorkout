package com.example.a7minutesWorkout

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import com.example.a7minutesworkout.R
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    // Variables to check view, metric or imperial
    private val metricUnitView = "METRIC_UNIT_VIEW"
    private val imperialUnitView = "IMPERIAL_UNIT_VIEW"

    private var currentVisibleView: String = metricUnitView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        setSupportActionBar(findViewById(R.id.toolbarBMIActivity))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"

        findViewById<Toolbar>(R.id.toolbarBMIActivity).setNavigationOnClickListener {
            onBackPressed()
        }

        // Change the color of the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimary)
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

        makeMetricUnitsViewVisible()
        findViewById<RadioGroup>(R.id.rgUnits).setOnCheckedChangeListener { _, checkedId ->
            if(checkedId == R.id.rbMetricUnits)
                makeMetricUnitsViewVisible()
            else
                makeImperialUnitsViewVisible()
        }
        

    }

    // Function to make Metric Units View available
    private fun makeMetricUnitsViewVisible() {
        currentVisibleView = metricUnitView
        findViewById<TextInputLayout>(R.id.textInputMetricUnitWeight).visibility = View.VISIBLE
        findViewById<TextInputLayout>(R.id.textInputMetricUnitHeight).visibility = View.VISIBLE

        findViewById<TextInputLayout>(R.id.textInputImperialUnitsWeight).visibility = View.GONE
        findViewById<LinearLayout>(R.id.linearLayoutImperialUnitsHeight).visibility = View.GONE

        findViewById<LinearLayout>(R.id.displayBMIResults).visibility = View.GONE

        // Clear edit texts
        findViewById<AppCompatEditText>(R.id.editTextMetricUnitWeight).text!!.clear()
        findViewById<AppCompatEditText>(R.id.editTextMetricUnitHeight).text!!.clear()
    }

    // Function to make Imperial Units View available
    private fun makeImperialUnitsViewVisible() {
        currentVisibleView = imperialUnitView
        findViewById<TextInputLayout>(R.id.textInputMetricUnitWeight).visibility = View.GONE
        findViewById<TextInputLayout>(R.id.textInputMetricUnitHeight).visibility = View.GONE

        findViewById<TextInputLayout>(R.id.textInputImperialUnitsWeight).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.linearLayoutImperialUnitsHeight).visibility = View.VISIBLE

        findViewById<LinearLayout>(R.id.displayBMIResults).visibility = View.GONE

        // Clear edit texts
        findViewById<AppCompatEditText>(R.id.editTextMetricUnitWeight).text!!.clear()
        findViewById<AppCompatEditText>(R.id.editTextImperialUnitHeightFeet).text!!.clear()
        findViewById<AppCompatEditText>(R.id.editTextImperialUnitHeightInch).text!!.clear()
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

        // val textViewYourBMI = findViewById<TextView>(R.id.textViewYourBMI)
        val textViewBMIType = findViewById<TextView>(R.id.textViewBMIType)
        val textViewBMIValue = findViewById<TextView>(R.id.textViewBMIValue)
        val textViewBMIDescription = findViewById<TextView>(R.id.textViewBMIDescription)

        findViewById<LinearLayout>(R.id.displayBMIResults).visibility = View.VISIBLE

        /*textViewYourBMI.visibility = View.VISIBLE
        textViewBMIValue.visibility = View.VISIBLE
        textViewBMIType.visibility = View.VISIBLE
        textViewBMIDescription.visibility = View.VISIBLE*/

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