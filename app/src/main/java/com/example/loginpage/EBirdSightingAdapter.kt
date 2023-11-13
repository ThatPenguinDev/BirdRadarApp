package com.example.loginpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class EBirdSightingAdapter(private val eBirdSightings: List<EBirdSighting>) :
    RecyclerView.Adapter<EBirdSightingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val speciesNameTextView: TextView = itemView.findViewById(R.id.textSpeciesName)
        val locationTextView: TextView = itemView.findViewById(R.id.textLocation)
        val dateTextView: TextView = itemView.findViewById(R.id.textDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_ebird, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eBirdSighting = eBirdSightings[position]
        holder.speciesNameTextView.text = eBirdSighting.speciesName
        holder.locationTextView.text = eBirdSighting.location
        holder.dateTextView.text = eBirdSighting.date
    }

    override fun getItemCount(): Int {
        return eBirdSightings.size
    }
}
