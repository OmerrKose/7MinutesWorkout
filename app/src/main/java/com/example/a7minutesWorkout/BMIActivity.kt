package com.example.a7minutesWorkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.example.a7minutesworkout.R
import java.lang.Exception

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        setSupportActionBar(findViewById(R.id.toolbarBMIActivity))
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }

        findViewById<Toolbar>(R.id.toolbarBMIActivity).setNavigationOnClickListener {
            onBackPressed()
        }

    }
}