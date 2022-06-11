package com.mobile.liderstoreagent.ui.screens

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.ToAgentSellOrder
import com.mobile.liderstoreagent.data.models.warehouse_product.OwnProduct
import com.mobile.liderstoreagent.data.models.warehouse_product.Result
import com.mobile.liderstoreagent.databinding.ScreenOwnProductsBinding
import com.mobile.liderstoreagent.ui.adapters.OwnProductListAdapter
import com.mobile.liderstoreagent.ui.viewmodels.*
import com.mobile.liderstoreagent.utils.showToast
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch

class OwnProductScreen : Fragment(R.layout.screen_own_products) {

    private val binding by viewBinding(ScreenOwnProductsBinding::bind)
    private val pageViewModel by lazy { initViewModel() }
//    private val historyViewModel: HistoryViewModel by viewModels()
    private lateinit var adapter :OwnProductListAdapter
    private var list: PagingData<Result>? = null


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            backToHomeClientProducts.setOnClickListener {
                findNavController().navigateUp()
            }

            val handler = Handler(Looper.getMainLooper())
            searchClientProductView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    handler.removeCallbacksAndMessages(null)
                    searching(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({
                        searching(newText)
                    }, 500)
                    return true
                }

            })

//            searchByDate.setOnClickListener {
//                if (list != null) {
//                    val c = Calendar.getInstance()
//                    val year = c.get(Calendar.YEAR)
//                    val month = c.get(Calendar.MONTH)
//                    val day = c.get(Calendar.DAY_OF_MONTH)
//                    val datePickerDialog = DatePickerDialog(
//                        requireActivity(), { view, year, monthOfYear, dayOfMonth ->
//
//                            val dayStr = if (dayOfMonth < 10) "0${dayOfMonth}" else "${dayOfMonth}"
//                            val mon = monthOfYear + 1
//                            val monthStr = if (mon < 10) "0${mon}" else "${mon}"
//
//                            val searchForThis = "$year-${monthStr}-$dayStr"
//                            var productSearched = ArrayList<Result>()
//                            for (element in list) {
//                                if (element.created_date.contains(searchForThis)) {
//                                    productSearched.add(element)
//                                }
//                            }
//
//                            if (productSearched.isNotEmpty()) {
//                                adapter.data = productSearched
//                                adapter.notifyDataSetChanged()
//                            } else {
//                                requireContext().showToast("Топилмади")
//                            }
//
//                        },
//                        year,
//                        month,
//                        day
//                    )
//                    datePickerDialog.show()
//                } else {
//                    requireActivity().showToast("Ҳали маҳсулот юқ")
//                }
//
//            }

            setObservers()
//            historySetUp()
        }
    }

    private fun initViewModel() = ViewModelProvider(
        this, OwnViewModelFactory(requireContext())
    ).get(OwnProductViewModel::class.java)

    private fun searching(std: String?) {
//        if (std != null) {
//            val trim = std.trim()
//            adapter.data = list.filter {
//                it.product.name.contains(trim, true)
//            }
//            adapter.notifyDataSetChanged()
//        }

        pageViewModel.getPro(std?:"").observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

    }

    private fun setObservers() {
        pageViewModel.getPro("").observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter = OwnProductListAdapter()
                binding.recyclerClientProducts.adapter = adapter
                adapter.submitData(it)

//                if (products.results.isEmpty()) {
//                    requireContext().showToast("Маҳсулот топилмади")
//                }
            }
        }
    }

//    private fun historySetUp() {
//        historyViewModel.errorHistoryLiveData.observe(viewLifecycleOwner,
//            errorProductsObserver)
//        historyViewModel.progressHistoryLiveData.observe(viewLifecycleOwner, progressObserver)
//
//        historyViewModel.connectionErrorHistoryLiveData.observe(
//            viewLifecycleOwner,
//            connectionErrorObserver
//        )
//
//        historyViewModel.successHistoryLiveData.observe(
//            viewLifecycleOwner,
//            successOwnCategoriesObserver
//        )
//    }

    private val progressObserver = Observer<Boolean> {
            binding.clientProductsProgressBar.isVisible = it

    }

    private val errorProductsObserver = Observer<Unit> {
        //requireActivity().showToast("Уланишда хатолик!")

        // fors major
        binding.clientProductsProgressBar.isVisible =false
        requireActivity().showToast("Амалга оширилди    !")

    }


    private val successOwnCategoriesObserver = Observer<ToAgentSellOrder> { dataTo ->
//        data = dataTo as ToAgentSellOrder
    }

    private val successProductsObserver = Observer<OwnProduct> { products ->

    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }

//    private fun onReturnedClick(model:ProductSm) {
//        val returnedInputDialog = ReturnedInputDialog(requireContext())
//        returnedInputDialog.setOnAmountInput { it1, it2 ->
//            historyViewModel.getProduct(model.id.toString(), ToAgentSellOrder(
//                model.quantity,
//                it1.toString(),
//                it2,
//                TokenSaver.getAgentId(),
//                model.product.id
//            ))
//
//        }
//        returnedInputDialog.show()
//    }

}