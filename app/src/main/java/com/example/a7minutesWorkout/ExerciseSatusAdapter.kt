package com.example.a7minutesWorkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.R

// Class for the recycler view of the exercises
class ExerciseStatusAdapter(private val items: ArrayList<ExerciseModel>, private val context: Context) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item: TextView = view.findViewById(R.id.itemTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_exercise_status, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.item.text = model.getId().toString()

        // Change the background of the passed exercises
        when {
            // If user is in at the current exercise
            model.getIsSelected() -> {
                holder.item.background = ContextCompat.getDrawable(context, R.drawable.item_circular_thin_color_accent_border)
                holder.item.setTextColor(Color.BLACK)
            }
            // If the user has completed the exercise
            model.getIsCompleted() -> {
                holder.item.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
                holder.item.setTextColor(Color.WHITE)
            }
            // If the user has not completed the exercise
            else -> {
                holder.item.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_gray_background)
                holder.item.setTextColor(Color.BLACK)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
