package com.example.feelbetter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.feelbetter.fragments.InspiringFragment
import com.example.feelbetter.fragments.MemoriesFragment
import com.example.feelbetter.fragments.TodoFragment

class TabPageAdaptor (activity: FragmentActivity, private  val tabCount: Int) : FragmentStateAdapter(activity)
{
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return  when (position)
        {
            0 -> TodoFragment()
            1 -> MemoriesFragment()
            2 -> InspiringFragment()
            else -> TodoFragment()
        }
    }

}