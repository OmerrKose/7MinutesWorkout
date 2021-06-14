package com.example.a7minutesWorkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // Database variables
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "7MinutesWorkoutDB"
        private const val TABLE_HISTORY = "history"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_COMPLETED_DATE = "completed_date"
    }

    // Create table history (_id INTEGER PRIMARY KEY, completedDate TEXT)
    override fun onCreate(db: SQLiteDatabase?) {
        val createExerciseTable =
            ("CREATE TABLE $TABLE_HISTORY($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_COMPLETED_DATE TEXT)")
        db?.execSQL(createExerciseTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Delete the table and insert the new one
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
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

    /*
    * Function that creates a list to read from the database,
    * will read until the end of the database and add it to the created list,
    * returns the list in String array.
    */
    fun getAllCompleteDatesList() : ArrayList<String> {
        val list = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)

        while(cursor.moveToNext()) {
            val date = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
            list.add(date)
        }
        // Close the cursor
        cursor.close()

        return list
    }
}