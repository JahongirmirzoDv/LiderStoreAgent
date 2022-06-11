package com.mobile.liderstoreagent.ui.screens

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.ToAgentSellOrder
import com.mobile.liderstoreagent.databinding.FragmentSoldProductBinding
import com.mobile.liderstoreagent.databinding.ReturnedInputBinding
import com.mobile.liderstoreagent.ui.adapters.SoldOwnProductHistoryAdapter
import com.mobile.liderstoreagent.ui.viewmodels.HistoryViewModel
import com.mobile.liderstoreagent.ui.viewmodels.SoldProductFactory
import com.mobile.liderstoreagent.ui.viewmodels.SoldProductViewModel
import com.mobile.liderstoreagent.utils.NetworkHelper
import com.mobile.liderstoreagent.utils.showToast
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch

class SoldProductFragment : Fragment(R.layout.fragment_sold_product) {

    private val binding by viewBinding(FragmentSoldProductBinding::bind)
    private lateinit var myViewModel: SoldProductViewModel
    private lateinit var recAdapter: SoldOwnProductHistoryAdapter
    private val historyViewModel: HistoryViewModel by viewModels()
    private var pagingData: PagingData<com.mobile.liderstoreagent.data.models.historymodel.other.Result>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recAdapter = SoldOwnProductHistoryAdapter()

        myViewModel = ViewModelProvider(requireActivity(), SoldProductFactory(NetworkHelper(requireContext())))[SoldProductViewModel::class.java]
        binding.apply {

            historySetUp()
            recyclerClientProducts.adapter = recAdapter

            myViewModel.getSoldList("").observe(viewLifecycleOwner, {
                viewLifecycleOwner.lifecycleScope.launch {
                    recAdapter.submitData(it)
                }
            })

        }

        recAdapter.returnClickListener {
            onReturnedClick(it)
        }
    }

    private fun onReturnedClick(model: com.mobile.liderstoreagent.data.models.soldownproductlist.Result) {
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
                        historyViewModel.getProduct1(
                            "order/agent-to-client-sell-order/${model.id}/", ToAgentSellOrder(
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
//        data = dataTo as ToAgentSellOrder
    }

    private val progressObserver = Observer<Boolean> {
//        productsProgressBar.isVisible = it
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }
    private val errorHistorysObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SoldProductFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}