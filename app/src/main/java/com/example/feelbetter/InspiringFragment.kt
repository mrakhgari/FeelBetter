package com.example.feelbetter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feelbetter.adapters.InspiringContentAdapter
import com.example.feelbetter.datasets.InspiringContentDataset

class InspiringFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inspiring, container, false)
        // Inflate the layout for this fragment
        Log.i("TAG_EE", "onCreate: ", )
        val inspiringDataset = InspiringContentDataset().loadInspiring()
        Log.i("TAG_EE", "onCreate1: ", )
        val recyclerView = view?.findViewById<RecyclerView>(R.id.inspiring_fragment_rv)
        Log.i("TAG_EE", "onCreate2: " + recyclerView.toString()  + view.toString())
        recyclerView?.adapter = InspiringContentAdapter(activity, inspiringDataset)
        Log.i("TAG_EE", "onCreate:3 ", )
        recyclerView?.setHasFixedSize(true)
        Log.i("TAG_EE", "onCreate: ", )
        return view
    }

}