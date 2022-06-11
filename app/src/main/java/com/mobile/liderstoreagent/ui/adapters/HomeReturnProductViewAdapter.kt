package com.mobile.liderstoreagent.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobile.liderstoreagent.ui.screens.ClientProductsFragment

class HomeReturnProductViewAdapter(var n: Int, var f: Float, fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return ClientProductsFragment.newInstance(n, f)
    }
}