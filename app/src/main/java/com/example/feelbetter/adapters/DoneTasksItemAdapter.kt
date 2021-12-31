package com.example.feelbetter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feelbetter.R
import com.example.feelbetter.models.Task
import kotlinx.android.synthetic.main.list_row.view.*

open class DoneTasksItemAdapter(private val context: Context, private var list: ArrayList<Task>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.done_list_row, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if (holder is MyViewHolder) {
            holder.itemView.name.text = model.task
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}