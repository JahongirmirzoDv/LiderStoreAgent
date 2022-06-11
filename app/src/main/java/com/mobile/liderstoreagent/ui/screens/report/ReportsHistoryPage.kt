package com.mobile.liderstoreagent.ui.screens.report

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
import com.mobile.liderstoreagent.data.models.reportmodel.ReportHistory
import com.mobile.liderstoreagent.ui.adapters.reporthistory.ReportHistoryAdapter
import com.mobile.liderstoreagent.ui.dialogs.ClientChooseDialog
import com.mobile.liderstoreagent.ui.viewmodels.ClientPageViewModel
import com.mobile.liderstoreagent.ui.viewmodels.reporthistory.ReportHistoryViewModel
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.history_reports_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class ReportsHistoryPage : Fragment(R.layout.history_reports_fragment) {
    private val viewModel: ReportHistoryViewModel by viewModels()
    private val historyReportAdapter by lazy { ReportHistoryAdapter() }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            reportHistoryProgressBar.visibility = View.VISIBLE
        } else {
            reportHistoryProgressBar.visibility = View.GONE
        }
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }

    private val errorHistoryObserver = Observer<String> {
        requireActivity().showToast(it)
        reportHistoryProgressBar.visibility = View.GONE
    }

    private val successHistoryGet = Observer<List<ReportHistory>> { historyList ->
        initRecycler(historyList)
        reportData = historyList
    }

    @SuppressLint("FragmentLiveDataObserve")

    fun reportHistorySetUp() {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorNotFoundLiveData.observe(this, errorNotFoundObserver)
        viewModel.errorLiveData.observe(this, errorHistoryObserver)
        viewModel.successLiveDataGet.observe(this, successHistoryGet)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
    }


    private fun initRecycler(history: List<ReportHistory>) {
        historyReportAdapter.submitList(history)
        recyclerReportHistory.layoutManager = LinearLayoutManager(requireContext())
        recyclerReportHistory.adapter = historyReportAdapter
    }


    var clientId = -1
    private var querySt = ""
    private val viewModelClient: ClientPageViewModel by viewModels()
    var clientsData: List<ClientsData> = ArrayList()

    var reportData: List<ReportHistory> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToHomeReportsHistory.setOnClickListener {
            findNavController().navigateUp()
        }
        reportHistorySetUp()
        clientsSetUp()



        searchByDate.setOnClickListener {
            if (reportData.isNotEmpty()) {
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
                        var productSearched = ArrayList<ReportHistory>()
                        for (element in reportData) {
                            if (element.created_date.contains(searchForThis)) {
                                productSearched.add(element)
                            }
                        }

                        if (productSearched.isNotEmpty()) {
                            initRecycler(productSearched)
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


        val closeButton = searchReport.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            viewModel.closeSearch()
        }


        val handler = Handler()
        searchReport.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                if (query != null) {
                    querySt = query.trim()
                    initRecycler(reportData.filter {
                            it.comment.contains(
                                querySt,
                                true
                            )
                    })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    if (newText != null) {
                        querySt = newText.trim()
                        initRecycler(reportData.filter {
                            it.comment.contains(
                                querySt,
                                true
                            )
                        })
                    }
                }, 500)
                return true
            }
        })


    }


    private val closeSearchObserver = Observer<Unit> {
        searchReport.setQuery(null, false)
        searchReport.clearFocus()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private val errorClientsObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
        reportHistoryProgressBar.visibility = View.GONE

    }


    private val successClientsObserver = Observer<List<ClientsData>> { list ->
        clientsData = list
    }

    private fun clientChosenChooseDialog(data: List<ClientsData>) {
        val dialog = ClientChooseDialog(requireContext(), data)
        dialog.show()
        dialog.setOnClientChosen { id, name, totalDebt ->
            clientId = id
            viewModel.getReports(clientId.toString())
        }
    }

    private fun clientsSetUp() {
        viewModelClient.closeLiveData.observe(viewLifecycleOwner, closeSearchObserver)
        viewModelClient.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModelClient.errorCategoriesLiveData.observe(viewLifecycleOwner, errorClientsObserver)
        viewModelClient.connectionErrorLiveData.observe(viewLifecycleOwner, connectionErrorObserver)
        viewModelClient.successLiveData.observe(viewLifecycleOwner, successClientsObserver)
        viewModelClient.errorServerLiveData.observe(viewLifecycleOwner, serverErrorObserver)

    }

    private val serverErrorObserver = Observer<String> { it ->
        requireActivity().showToast(it)
        reportHistoryProgressBar.visibility = View.GONE
    }

//    private val errorTimeOutObserver = Observer<Unit> {
//        requireActivity().showToast("Интернет жуда паст!")
//        clientProductsProgressBar.visibility = View.GONE
//    }
//
//
    private val errorNotFoundObserver = Observer<String> {
        reportHistoryProgressBar.visibility = View.GONE
        requireActivity().showToast(it)
    }
}

