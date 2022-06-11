package com.mobile.liderstoreagent.ui.pages

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.ClientProducts
import com.mobile.liderstoreagent.ui.adapters.ClientProductListAdapterForView
import com.mobile.liderstoreagent.ui.dialogs.ClientChooseDialog
import com.mobile.liderstoreagent.ui.viewmodels.ClientPageViewModel
import com.mobile.liderstoreagent.ui.viewmodels.ClientProductsViewModel
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.products_fragment.*
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class ProductsPage : Fragment(R.layout.products_fragment) {

    var clientId = -1
    private var querySt = ""
    private val viewModel: ClientPageViewModel by viewModels()
    var clientsData: List<ClientsData> = ArrayList()

    private val productsAdapter by lazy { ClientProductListAdapterForView() }
    private val pageViewModel: ClientProductsViewModel by viewModels()
    var productData: List<ClientProducts> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clientsSetUp()
        productsSetUp()


        searchByDate.setOnClickListener {
            if (productData.isNotEmpty()) {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)


                val dpd = DatePickerDialog(
                    requireActivity(),
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        val dayStr = if (dayOfMonth < 10) "0${dayOfMonth}" else "${dayOfMonth}"
                        val mon = monthOfYear + 1
                        val monthStr = if (mon < 10) "0${mon}" else "${mon}"

                        val searchForThis = "$year-${monthStr}-$dayStr"
                        var productSearched = ArrayList<ClientProducts>()
                        for (element in productData) {
                            if (element.created_date.contains(searchForThis)) {
                                productSearched.add(element)
                            }
                        }

                        if (productSearched.isNotEmpty()) {
                            initProductsList(productSearched)
                        } else {
                            requireContext().showToast("Топилмади")
                        }

                    },
                    year,
                    month,
                    day
                )

                dpd.show()
            } else {
                requireContext().showToast("Ҳали маҳсулот юқ")
            }
        }


        searchByClient.setOnClickListener {
            if (clientsData.isNotEmpty()) {
                clientChosenChooseDialog(clientsData)
            } else requireActivity().showToast("Ҳаридорлар листи топилмади!")
        }


        val closeButton = searchClientProductView.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            viewModel.closeSearch()
        }


        val handler = Handler()
        searchClientProductView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                if (query != null) {
                    querySt = query.trim()
                    initProductsList(productData.filter {
                        if (it.product.code == null) {
                            it.product.name.contains(
                                querySt,
                                true
                            )
                        } else{
                            it.product.name.contains(
                                querySt,
                                true
                            ) || it.product.code.contains(
                                querySt,
                                true
                            )
                        }
                    })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    if (newText != null) {
                        querySt = newText.trim()
                        initProductsList(productData.filter {
                            if (it.product.code == null) {
                                it.product.name.contains(
                                    querySt,
                                    true
                                )
                            } else{
                                it.product.name.contains(
                                    querySt,
                                    true
                                ) || it.product.code.contains(
                                    querySt,
                                    true
                                )
                            }
                        })
                    }
                }, 500)
                return true
            }
        })


    }


    private val closeSearchObserver = Observer<Unit> {
        searchClientProductView.setQuery(null, false)
        searchClientProductView.clearFocus()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            clientProductsProgressBar.visibility = View.VISIBLE
        } else {
            clientProductsProgressBar.visibility = View.GONE
        }
    }

    private val errorClientsObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
        clientProductsProgressBar.visibility = View.GONE

    }


    private val errorReturnedResLiveData = Observer<String> {
        requireActivity().showToast(it)
        clientProductsProgressBar.visibility = View.GONE

    }
    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }
    private val successClientsObserver = Observer<List<ClientsData>> { list ->
        clientsData = list
        if (list.isNotEmpty()) {
            clientId = list[0].client.id
            clientNameTitle.text = list[0].client.name
            if (list[0].total_debt == 0.0) clientDebtTitle.text = "0"
            else clientDebtTitle.text = list[0].total_debt.toString()
            pageViewModel.getClientProducts(clientId)

        }
    }

    private fun clientChosenChooseDialog(data: List<ClientsData>) {
        val dialog = ClientChooseDialog(requireContext(), data)
        dialog.show()
        dialog.setOnClientChosen { id, name, totalDebt ->
            clientNameTitle.text = name
            if (totalDebt == 0.0) clientDebtTitle.text = "0"
            else clientDebtTitle.text = totalDebt.toString()
            clientId = id
            pageViewModel.getClientProducts(clientId)
        }
    }

    private fun clientsSetUp() {
        viewModel.closeLiveData.observe(viewLifecycleOwner,closeSearchObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.errorCategoriesLiveData.observe(viewLifecycleOwner, errorClientsObserver)
        viewModel.connectionErrorLiveData.observe(viewLifecycleOwner, connectionErrorObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successClientsObserver)
        viewModel.errorServerLiveData.observe(viewLifecycleOwner,serverErrorObserver)

    }

    private val serverErrorObserver = Observer<String> { it ->
        requireActivity().showToast(it)
        clientProductsProgressBar.visibility = View.GONE
    }
    fun productsSetUp() {
        pageViewModel.progressProductsLiveData.observe(viewLifecycleOwner, progressObserver)
        pageViewModel.errorProductsLiveData.observe(viewLifecycleOwner, errorProductsObserver)
        pageViewModel.successProductsLiveData.observe(viewLifecycleOwner, successProductsObserver)
        pageViewModel.connectionErrorProductsLiveData.observe(viewLifecycleOwner, connectionErrorObserver)

        pageViewModel.progressReturnedLiveData.observe(viewLifecycleOwner, progressObserver)
        pageViewModel.errorReturnedLiveData.observe(viewLifecycleOwner, errorProductsObserver)
        pageViewModel.errorReturnedResponeLiveData.observe(viewLifecycleOwner, errorReturnedResLiveData)
        pageViewModel.connectionErrorReturnedLiveData.observe(viewLifecycleOwner, connectionErrorObserver)
        pageViewModel.errorTimeOutLive.observe(viewLifecycleOwner, errorTimeOutObserver)
        pageViewModel.successReturnedLiveData.observe(viewLifecycleOwner, successReturnedProduct)

    }


    private val successReturnedProduct = Observer<Any> {
        requireActivity().showToast("Маҳсулот қайтарилди!")
    }


    private val errorTimeOutObserver = Observer<Unit> {
        requireActivity().showToast("Интернет жуда паст!")
        clientProductsProgressBar.visibility = View.GONE
    }


    private val errorProductsObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
    }

    private val successProductsObserver = Observer<List<ClientProducts>> { products ->
        productData = products
        initProductsList(products)
    }

    fun initProductsList(data: List<ClientProducts>) {
        productsAdapter.submitList(data)
        recyclerClientProducts.layoutManager = LinearLayoutManager(requireContext())
        recyclerClientProducts.adapter = productsAdapter
    }
}