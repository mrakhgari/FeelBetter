package com.example.feelbetter.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.feelbetter.R
import com.example.feelbetter.models.InspiringContent

class InspiringContentAdapter(
    private val context: FragmentActivity?,
    private val dataset: List<InspiringContent>
) :
    RecyclerView.Adapter<InspiringContentAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.inspiring_container, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: InspiringContent = dataset[position]
        Log.i("TAG_EE", "onBindViewHolder: "+context?.resources?.getString(item.titleResourceId))
        holder.titleTextView.text = context!!.resources.getString(item.titleResourceId)
        holder.descriptionTextView.text = context.resources.getString(item.descResourceId)
        holder.imageView.setImageResource(item.imageResourceId)
    }

    override fun getItemCount() = dataset.size


    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.inspiring_card_image)
        val titleTextView: TextView = view.findViewById(R.id.inspiring_card_title)
        val descriptionTextView: TextView = view.findViewById(R.id.inspiring_card_desc)

    }

}
