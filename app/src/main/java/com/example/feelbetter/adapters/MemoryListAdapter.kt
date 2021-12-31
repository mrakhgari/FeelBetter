package com.example.feelbetter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feelbetter.R
import com.example.feelbetter.models.Memory
import kotlinx.android.synthetic.main.memory_list_item.view.*

class MemoryListAdapter(
    private val onMemoryClickListener: OnMemoryClickListener
) :
    RecyclerView.Adapter<MemoryListAdapter.MemoryListViewHolder>() {

    private val memoryList = mutableListOf<Memory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MemoryListViewHolder.from(parent, this.onMemoryClickListener)

    override fun onBindViewHolder(holder: MemoryListViewHolder, position: Int) {
        holder.bind(memoryList[position])
    }

    override fun getItemCount() = memoryList.size

    fun submitItem(memory: Memory) {
        memoryList.add(0, memory)
        notifyDataSetChanged()
    }

    fun clearList() {
        if (memoryList.isNotEmpty()) {
            memoryList.removeAll(memoryList)
            notifyDataSetChanged()
        }

    }

    class MemoryListViewHolder(
        itemView: View,
        onMemoryClickListener: OnMemoryClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        private lateinit var memory: Memory

        init {
            itemView.memory_item_delete.setOnClickListener {
                onMemoryClickListener.onMemoryDelete(memory)
            }
        }

        internal fun bind(memory: Memory) {
            this.memory = memory

            itemView.memory_item_title.text = memory.title
            itemView.memory_item_date.text = memory.date
            itemView.memory_item_des.text = memory.description
        }

        companion object {

            fun from(
                parent: ViewGroup,
                onMemoryClickListener: OnMemoryClickListener
            ) = MemoryListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.memory_list_item, parent, false),
                onMemoryClickListener
            )
        }
    }
}

interface OnMemoryClickListener {

    fun onMemoryDelete(memory: Memory)
}