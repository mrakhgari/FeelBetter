package com.example.feelbetter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider

import androidx.viewpager.widget.ViewPager
import com.example.feelbetter.R
import com.example.feelbetter.fragments.children.TodoFragment
import com.google.android.material.tabs.TabLayout

class TasksFragment : Fragment() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var todoFragment = TodoFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        requireFragmentManager().beginTransaction().replace(R.id.fragment_container, todoFragment).commit()

        return view
    }


//    private fun addFragment(view: View) {
//        tabLayout = view.findViewById<View>(R.id.tasks_tabLayout) as TabLayout?
//        viewPager = view.findViewById<View>(R.id.tasks_view_pager) as ViewPager?
//        val viewPageAdaptor = ViewPageAdaptor(childFragmentManager)
//        viewPageAdaptor.addFragment(TodoFragment() , "Todo")
//        viewPageAdaptor.addFragment(DoneFragment() , "Done")
//        viewPager?.adapter = viewPageAdaptor
//        tabLayout?.setupWithViewPager(viewPager)
//    }

}