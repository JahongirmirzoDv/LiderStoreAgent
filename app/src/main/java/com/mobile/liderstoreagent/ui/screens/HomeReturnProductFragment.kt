package com.mobile.liderstoreagent.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.databinding.FragmentHomeReturnProductBinding
import com.mobile.liderstoreagent.ui.adapters.HomeReturnProductViewAdapter
import com.mobile.liderstoreagent.utils.hideKeyboard
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class HomeReturnProductFragment : Fragment(R.layout.fragment_home_return_product) {

    private val binding by viewBinding(FragmentHomeReturnProductBinding::bind)
    private lateinit var homeReturnProductViewAdapter: HomeReturnProductViewAdapter
    private var n = 0
    private var f = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            n = it.getInt("Int")
            f = it.getFloat("Float")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            backToHomeClientProducts.setOnClickListener {
                (activity as AppCompatActivity).findViewById<SearchView>(R.id.searchClientProductView).hideKeyboard(requireActivity())
                findNavController().navigateUp()
            }

            homeReturnProductViewAdapter = HomeReturnProductViewAdapter(n, f, requireActivity())
            viewPager2.adapter = homeReturnProductViewAdapter

            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                when(position) {
                    0 -> {
                        tab.text = "Return${position+1}"
                    }
                    1 -> {
                        tab.text = "Return${position+1}"
                    }
                }

            }.attach()

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeReturnProductFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}