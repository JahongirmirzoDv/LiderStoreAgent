package com.mobile.liderstoreagent.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobile.liderstoreagent.databinding.FragmentProfileBinding
import com.mobile.liderstoreagent.ui.screens.HistoryScreen
import com.mobile.liderstoreagent.ui.screens.Profit
import com.mobile.liderstoreagent.ui.screens.SoldProductFragment

class HomeHistoryViewAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Profit()
            1 -> HistoryScreen()
            2 -> SoldProductFragment()
            else -> HistoryScreen()
        }
    }
}