package com.mobile.liderstoreagent.ui.screens

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.ToAgentSellOrder
import com.mobile.liderstoreagent.data.models.historymodel.other.Result
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.HistoryClientApi
import com.mobile.liderstoreagent.databinding.ReturnedInputBinding
import com.mobile.liderstoreagent.databinding.ScreenHistoryBinding
import com.mobile.liderstoreagent.domain.repositories.HistoryProductRepository
import com.mobile.liderstoreagent.ui.adapters.HistoryAdapter
import com.mobile.liderstoreagent.ui.adapters.ListLoadStateAdapter
import com.mobile.liderstoreagent.ui.viewmodels.HistoryProductViewModel
import com.mobile.liderstoreagent.ui.viewmodels.HistoryViewModel
import com.mobile.liderstoreagent.ui.viewmodels.HistoryViewModelFactory
import com.mobile.liderstoreagent.utils.showToast
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryScreen : Fragment(R.layout.screen_history) {

    private val binding by viewBinding(ScreenHistoryBinding::bind)

    private val pagerViewModel by lazy { initViewModel() }
    private val historyViewModel: HistoryViewModel by viewModels()

    var data: ToAgentSellOrder? = null

    private val historyAdapter by lazy { HistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initRecyclerView()
        setRefreshListeners()
        historySetUp()

        historyAdapter.returnClickListener {
            onReturnedClick(it)
        }
    }

    private fun setObservers() = with(pagerViewModel) {
        getHistory("").observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                historyAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            historyAdapter.loadStateFlow.collectLatest { loadStates ->

                binding.clientProductsProgressBar.isVisible =
                    loadStates.refresh is LoadState.Loading
//                binding.srlRefresh.isRefreshing = loadStates.refresh is LoadState.Loading

                loadStates.refresh.let {
                    if (it is LoadState.Error) {
                        Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }





    private fun setRefreshListeners() = with(binding) {
//        srlRefresh.setOnRefreshListener {
//            historyAdapter.refresh()
//        }
    }

    private fun initRecyclerView() = with(binding) {

        recyclerClientProducts.adapter = historyAdapter.withLoadStateHeaderAndFooter(
            ListLoadStateAdapter { historyAdapter.retry() },
            ListLoadStateAdapter { historyAdapter.retry() }
        )

    }

    private fun initViewModel() = ViewModelProvider(
        this, HistoryViewModelFactory(
            requireContext(),
            HistoryProductRepository(ApiClient.retrofit.create(HistoryClientApi::class.java))
        )
    ).get(HistoryProductViewModel::class.java)

    private fun onReturnedClick(model: Result) {
        val dialog = AlertDialog.Builder(requireContext())
        val binding_dialog = ReturnedInputBinding.inflate(layoutInflater)
        dialog.setView(binding_dialog.root)
        val create = dialog.create()
        create.window?.setGravity(Gravity.CENTER)

        binding_dialog.apply {

            addAmountInput.setOnClickListener {
                val toString = returnedAmount.text.toString()
                if (toString.isNotEmpty()) {
                    if (model.quantity.toFloat()>toString.toFloat() && (model.last_returnded_quantity.toFloat()+toString.toFloat()) < model.quantity.toFloat()) {
                        historyViewModel.getProduct(
                            "order/to-agent-sell-order/${model.id}/", ToAgentSellOrder(
                                "returned", toString
                            )
                        )
                        create.dismiss()
                    } else {
                        requireActivity().showToast("микдор куп")
                    }
                } else {
                    requireActivity().showToast("Яроқли миқдор киритинг")
                }

            }

        }

        create.show()
    }

    private fun historySetUp() {
        historyViewModel.errorHistoryLiveData.observe(viewLifecycleOwner,
            errorHistorysObserver)
        historyViewModel.progressHistoryLiveData.observe(viewLifecycleOwner, progressObserver)

        historyViewModel.connectionErrorHistoryLiveData.observe(
            viewLifecycleOwner,
            connectionErrorObserver
        )

        historyViewModel.successHistoryLiveData.observe(
            viewLifecycleOwner,
            successOwnCategoriesObserver
        )
    }

    private val successOwnCategoriesObserver = Observer<ToAgentSellOrder> { dataTo ->
        data = dataTo as ToAgentSellOrder
    }

    private val progressObserver = Observer<Boolean> {
//        productsProgressBar.isVisible = it
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }
    private val errorHistorysObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
        //   productsProgressBar.visibility = View.GONE
    }

}