package com.example.a7minutesWorkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // Database variables
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "7MinutesWorkoutDB"
        private val TABLE_HISTORY = "history"
        private val COLUMN_ID = "_id"
        private val COLUMN_COMPLETED_DATE = "completed_date"
    }

    // Create table history (_id INTEGER PRIMARY KEY, completedDate TEXT)
    override fun onCreate(db: SQLiteDatabase?) {
        val createExerciseTable =
            ("CREATE TABLE " + TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_COMPLETED_DATE + " TEXT)")
        db?.execSQL(createExerciseTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Delete the table and insert the new one
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)
        onCreate(db)
    }

    fun addDate(date: String) {
        // Add more than one variable into the values
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE, date)

        val db = this.writableDatabase
        db.insert(TABLE_HISTORY, null, values)
        db.close() // Close the database
    }
}