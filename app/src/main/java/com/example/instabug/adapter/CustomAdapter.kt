package com.example.instabug.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instabug.R
import com.example.instabug.model.HtmlData


class CustomAdapter(private var data: List<HtmlData>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>()  {

        var dataLists : List<HtmlData> = data
        var filteredDataList : List<HtmlData> = data

    lateinit var mContext: Context


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view_item, parent, false)
            mContext = parent.context
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.name.text = dataLists[position].word
            holder.repeated.text = dataLists[position].occurrence.toString()


        }

        override fun getItemCount(): Int {
            return dataLists.size
        }

        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val name: TextView = itemView.findViewById(R.id.name)
            val repeated: TextView = itemView.findViewById(R.id.repeated)
        }



}

