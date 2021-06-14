package com.example.a7minutesWorkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.R

class HistoryAdapter(val context: Context, private val items: ArrayList<String>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val llHistoryMainItem: LinearLayout = view.findViewById(R.id.linearLayoutHistoryItemMain)
        val tvItem: TextView = view.findViewById(R.id.textViewItem)
        val tvPosition: TextView = view.findViewById(R.id.textViewPosition)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val  date: String = items[position]
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date

        if (position % 2 == 0)
            holder.llHistoryMainItem.setBackgroundColor(Color.parseColor("#EBEBEB"))
        else
            holder.llHistoryMainItem.setBackgroundColor(Color.WHITE)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}