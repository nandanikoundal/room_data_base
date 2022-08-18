package com.nandani.room_data_base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nandani.room_data_base.R

class RoomRecycler(var arrayList: ArrayList<Notes>) : RecyclerView.Adapter<RoomRecycler.ViewHolder>() {
    class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        var tvTitle = view.findViewById<TextView>(R.id.title)
        var tvDescription = view.findViewById<TextView>(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.custom_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvTitle.setText(arrayList[position].title)
        holder.tvDescription.setText(arrayList[position].description)
    }

    override fun getItemCount(): Int {
        return arrayList.size    }
}