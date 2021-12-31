package com.example.feelbetter.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feelbetter.R
import com.example.feelbetter.activities.AddMemoryActivity
import com.example.feelbetter.activities.log
import com.example.feelbetter.adapters.MemoryListAdapter
import com.example.feelbetter.adapters.OnMemoryClickListener
import com.example.feelbetter.models.Memory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_memories.*

class MemoriesFragment : Fragment(), OnMemoryClickListener {

    private val adapter by lazy { MemoryListAdapter(this) }

    val memoryList = mutableListOf<Memory>()

    private val databaseRef by lazy {
        Firebase.database.getReference("memory")
    }

    private val currentUser by lazy {
        FirebaseAuth.getInstance().currentUser
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memories_add.setOnClickListener {
            startActivity(
                Intent(context, AddMemoryActivity::class.java)
            )
        }

        memories_list.apply {
            adapter = this@MemoriesFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        loadMemories()
    }

    private fun loadMemories() {
        databaseRef.child(currentUser?.uid ?: "").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (memories_progressbar != null) {
                    memories_progressbar.visibility = View.GONE
                }

                adapter.clearList()

                snapshot.children.forEach {
                    val memory = Memory(
                        it.key.toString(),
                        it.child("title").value.toString(),
                        it.child("date").value.toString(),
                        it.child("description").value.toString()
                    )

                    memoryList.add(memory)
                    adapter.submitItem(memory)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                error.log()
            }
        })
    }

    override fun onMemoryDelete(memory: Memory) {
        databaseRef
            .child(currentUser?.uid ?: "")
            .child(memory.id)
            .removeValue()
    }
}