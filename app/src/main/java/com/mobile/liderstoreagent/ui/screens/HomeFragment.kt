package com.mobile.liderstoreagent.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.source.local.MyDatabase
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.ui.adapters.MainPageAdapter
import com.mobile.liderstoreagent.ui.viewmodels.HomeFragmentViewModel
import com.mobile.liderstoreagent.utils.pageChangeListener
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.main_nav.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class HomeFragment : Fragment(R.layout.main_nav) {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val adapter by lazy { MainPageAdapter(childFragmentManager) }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        agentName.text = "Агент: ${TokenSaver.getFirstName()}"
        Handler().postDelayed(Runnable {
            val d = TokenSaver.getClientCount().toString()
            if (d != "0") clientCount.text = d
        }, 500)


        addClient.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addClientsPage)
        }

        pager.adapter = adapter
        pager.pageChangeListener {
            viewModel.selectPagePosition(it)
        }

        val selectPageObserver = Observer<Int> {
            pager.currentItem = it
            when (it) {
                0 -> bottomNavigationView.selectedItemId = R.id.clients
                1 -> bottomNavigationView.selectedItemId = R.id.products
                2 -> bottomNavigationView.selectedItemId = R.id.Discounts
                else -> bottomNavigationView.selectedItemId = R.id.clients
            }
        }


        viewModel.selectPageLiveData.observe(viewLifecycleOwner, selectPageObserver)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.clients -> viewModel.selectPagePosition(0)
                R.id.products -> viewModel.selectPagePosition(1)
                R.id.Discounts -> viewModel.selectPagePosition(2)
                else -> viewModel.selectPagePosition(0)
            }
            return@setOnNavigationItemSelectedListener true
        }

        loadView()
    }


    private fun loadView() {
        menu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        image.setOnClickListener { drawerLayout.closeDrawer(GravityCompat.START) }

        profile.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
        }

        exit.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Диққат!")
                .setMessage("Тизимдан чиқишни ҳоҳлайсизми?")
                .setNegativeButton("Йўқ") { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton("Ҳа") { dialog, _ ->

                    val db = MyDatabase.getDatabase(requireContext())
                    GlobalScope.launch {
                        db.clearAllTables()
                    }

                    TokenSaver.token = ""
                    TokenSaver.setAgentId(0)
                    TokenSaver.setLogin("")
                    TokenSaver.setPassword("")
                    requireActivity().finish()
                    dialog.cancel()

                }.show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }


        soldProducts.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_soldProductsFragment)
        }


        agentExpenses.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_expensesFragment)
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        plan.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_planFragment)
        }

        report.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_reportFragment)
        }

        historyReports.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_reportsHistoryPage)
        }


        layoutHistory.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.homeHistoryFragment)
        }



        layoutProducts.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(HomeFragmentDirections.actionMainFragmentToOwnProductScreen())
        }


    }


}