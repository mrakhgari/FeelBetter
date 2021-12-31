package com.example.feelbetter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feelbetter.R
import com.example.feelbetter.models.Task
import kotlinx.android.synthetic.main.list_row.view.*

open class TasksItemAdapter(private val context: Context, private var list: ArrayList<Task>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var onEditListener: OnEditListener? = null
    private var onDeleteListener: OnDeleteListener? = null
    private var onDoneListener: OnDoneListener? = null

    interface OnDeleteListener {
        fun onClick(position: Int, model: Task)
    }

    fun setOnDeleteListener(onDeleteListener: OnDeleteListener) {
        this.onDeleteListener = onDeleteListener
    }

    interface OnEditListener {
        fun onClick(position: Int, model: Task)
    }

    fun setOnEditListener(onEditListener: OnEditListener) {
        this.onEditListener = onEditListener
    }

    interface OnDoneListener {
        fun onClick(position: Int, model: Task)
    }


    fun setOnDoneListener(onDoneListener: OnDoneListener) {
        this.onDoneListener = onDoneListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_row, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if (holder is MyViewHolder) {
            holder.itemView.name.text = model.task
            holder.itemView.done.setOnClickListener {
                if (onDoneListener != null) {
                    onDoneListener!!.onClick(position, model)
                }
            }
            holder.itemView.remove.setOnClickListener {
                if (onDeleteListener != null) {
                    onDeleteListener!!.onClick(position, model)
                }
            }
//
//            holder.itemView.edit.setOnClickListener {
//                if (onEditListener != null) {
//                    onEditListener!!.onClick(position, model)
//                }
//            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}