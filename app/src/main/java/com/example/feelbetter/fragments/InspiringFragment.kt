package com.example.feelbetter.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feelbetter.R
import com.example.feelbetter.adapters.InspiringContentAdapter
import com.example.feelbetter.datasets.InspiringContentDataset

class InspiringFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inspiring, container, false)
        // Inflate the layout for this fragment
        val inspiringDataset = InspiringContentDataset().loadInspiring()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.inspiring_fragment_rv)
        recyclerView?.adapter = InspiringContentAdapter(activity, inspiringDataset)
        recyclerView?.setHasFixedSize(true)
        return view
    }

}