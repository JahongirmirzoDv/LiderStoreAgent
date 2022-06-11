package com.mobile.liderstoreagent.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mobile.liderstoreagent.ui.pages.ClientsPage
import com.mobile.liderstoreagent.ui.pages.DiscountsPage
import com.mobile.liderstoreagent.ui.pages.ProductsPage

class MainPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var eventListener: ((Int) -> Unit)? = null

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fm = ClientsPage()
                fm
            }
            1 -> {
                val fm = ProductsPage()
                fm
            }
            2 -> {
                val fm = DiscountsPage()
                fm.eventDiscountListener { id ->
                    eventListener?.invoke(id)
                }
                fm
            }
            else -> {
                val fm = ClientsPage()
                fm
            }
        }
    }


}
