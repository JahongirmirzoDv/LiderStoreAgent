package com.mobile.liderstoreagent.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.databinding.FragmentHomeHistoryBinding
import com.mobile.liderstoreagent.ui.adapters.HomeHistoryViewAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class HomeHistoryFragment : Fragment(R.layout.fragment_home_history) {

    private val binding by viewBinding(FragmentHomeHistoryBinding::bind)
    private val homeHistoryViewAdapter by lazy { HomeHistoryViewAdapter(requireActivity()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            backToHomeClientProducts.setOnClickListener {
                findNavController().navigateUp()
            }

            viewPager2.adapter = homeHistoryViewAdapter

            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                when(position) {
                    0->{
                        tab.text = "Profit "
                    }
                    1 -> {
                        tab.text = "Olinganlar "
                    }
                    2 -> {
                        tab.text = "Sotilganlar"
                    }
                }

            }.attach()

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeHistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}