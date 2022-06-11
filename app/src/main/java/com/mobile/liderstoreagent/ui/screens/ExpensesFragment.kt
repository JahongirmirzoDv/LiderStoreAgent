package com.mobile.liderstoreagent.ui.screens

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
import com.mobile.liderstoreagent.data.models.expense.AgentExpenseData
import com.mobile.liderstoreagent.data.models.expense.AgentExpenseGet
import com.mobile.liderstoreagent.ui.adapters.ExpenseListAdapter
import com.mobile.liderstoreagent.ui.dialogs.ExpenseChooseDialog
import com.mobile.liderstoreagent.ui.viewmodels.GetExpensesViewModel
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.expenses_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class ExpensesFragment : Fragment(R.layout.expenses_fragment) {


    lateinit var expensesAdapter: ExpenseListAdapter
    private val viewModel: GetExpensesViewModel by viewModels()
    var chosenType = 1
    var expenseData: List<AgentExpenseData> = ArrayList()
    private var querySt = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        expenseSetUp()

        val closeButton = searchExpenses.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            viewModel.closeSearch()
        }


        filterByDate.setOnClickListener {

            if (expenseData.isNotEmpty()) {
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
                        var productSearched = ArrayList<AgentExpenseData>()
                        for (element in expenseData) {
                            if(element.created_date.contains(searchForThis)){
                                productSearched.add(element)
                            }
                        }

                        if(productSearched.isNotEmpty()){
                            initRecycler(productSearched)
                        }
                        else {
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


        filterExpenses.setOnClickListener {
            initExpenseFilterDialog()
        }


        backExpenses.setOnClickListener {
            findNavController().navigateUp()
        }
        refreshExpenses.setOnRefreshListener {
            viewModel.getExpenses()
            Handler().postDelayed(Runnable {
                refreshExpenses.isRefreshing = false
            }, 1000)
        }

        val handler = Handler()
        searchExpenses.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                if (query != null) {
                    querySt = query.trim()
                    initRecycler(expenseData.filter {
                        it.name.contains(
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
                        initRecycler(expenseData.filter {
                            it.name.contains(
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
        searchExpenses.setQuery(null, false)
        searchExpenses.clearFocus()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            expensesProgressBar.visibility = View.VISIBLE
        } else {
            expensesProgressBar.visibility = View.GONE
        }
    }
    private val errorExpensesObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
        expensesProgressBar.visibility = View.GONE
    }
    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }
    private val successExpensesObserver = Observer<AgentExpenseGet> { list ->
        expenseData = list.data
        setFilter(chosenType)
    }

    private val timeOutObserver = Observer<Unit> {
        expensesProgressBar.visibility = View.GONE
        requireActivity().showToast("Интернет ишламаяпди!")
    }

    private val notFoundObserver = Observer<String> {
        expensesProgressBar.visibility = View.GONE
        requireActivity().showToast(it)
    }


    fun initRecycler(data: List<AgentExpenseData>) {
        expensesAdapter = ExpenseListAdapter()
        expensesAdapter.submitList(data)
        expensesAdapter.query = querySt
        recyclerExpenses.layoutManager = LinearLayoutManager(requireContext())
        recyclerExpenses.adapter = expensesAdapter
        var summa = 0.0
        data.map { t-> summa+=t.quantity.toDouble() }
        summationExpenses.text = summa.toString()
    }

    private fun initExpenseFilterDialog() {
        val dialog = ExpenseChooseDialog(requireContext())
        dialog.setOnExpenseChosen { filterNumber ->
            chosenType = filterNumber
            setFilter(filterNumber)
        }
        dialog.show()
    }

    private fun setFilter(number: Int) {
        if (expenseData.isNotEmpty())
            when (number) {
                1 -> {
                    initRecycler(expenseData)
                }
                2 -> {
                    val list = ArrayList<AgentExpenseData>()
                    expenseData.map {
                        if (it.approved) {
                            list.add(it)
                        }
                    }
                    initRecycler(list)
                }
                3 -> {

                    val list = ArrayList<AgentExpenseData>()
                    expenseData.map {
                        if (it.category == "self") {
                            list.add(it)
                        }
                    }
                    initRecycler(list)

                }
                4 -> {

                    val list = ArrayList<AgentExpenseData>()
                    expenseData.map {
                        if (it.category == "firm") {
                            list.add(it)
                        }
                    }
                    initRecycler(list)
                }
            }
        else {
            requireActivity().showToast("Бўш!")
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun expenseSetUp() {
        viewModel.closeLiveData.observe(this, closeSearchObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorExpensesLiveData.observe(this, errorExpensesObserver)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
        viewModel.successLiveData.observe(this, successExpensesObserver)
        viewModel.errorTimeOutLiveData.observe(this, timeOutObserver)
        viewModel.errorExpensesNotFoundLiveData.observe(this, notFoundObserver)

    }

}