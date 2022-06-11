package com.mobile.liderstoreagent.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.agentbox.BoxCount
import com.mobile.liderstoreagent.data.models.expense.AgentExpensePost
import com.mobile.liderstoreagent.data.models.expense.ExpenseCategories
import com.mobile.liderstoreagent.data.models.salarymodel.SalaryData
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.ui.dialogs.SelectorDialog
import com.mobile.liderstoreagent.ui.viewmodels.SalaryViewModel
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private val viewModel: SalaryViewModel by viewModels()
    var expenseTypeText = "self"
    lateinit var expenseCategories: ExpenseCategories


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.successLiveData1.observe(viewLifecycleOwner, successBoxCountObserver)
        viewModel.getBoxCount()

        viewModel.getExpenseCategories()
        viewModel.successCategoryExpenseLiveData.observe(
            viewLifecycleOwner,
            successExpenseCatObserver
        )

        agent_firstname.text = TokenSaver.getFirstName()
        agent_lastname.text = TokenSaver.getLastName()
        agent_phone.text = TokenSaver.getLogin()


        //   textChangedListener(expenseInputCard)
        //   textChangedListener(expenseInputCash)


        soldHistorySetUp()
        expenseAddSetUp()
        expenseType.setOnCheckedChangeListener { group, checkedId ->
            expenseTypeText = if (R.id.radioFirm == checkedId) "firm" else "self"
        }

        backToHomeProfile.setOnClickListener {
            findNavController().navigateUp()
        }

        categorySpinner.setOnClickListener {
            var markets = ArrayList<String>()
            if (this::expenseCategories.isInitialized) {
                expenseCategories.results.map { t -> markets.add(t.name) }
                val dialog = SelectorDialog(requireContext(), markets)
                dialog.setSelectedName { it ->
                    categoryText.text = it
                }
                dialog.show()
            }
        }

        payPayment.setOnClickListener {
            val name = inputExpenseName.text.toString()
            val description = inputExpenseDescription.text.toString()
            if (expenseInputCash.text.toString().isNotEmpty()) {
                if (name.isNotEmpty() && description.isNotEmpty() && categoryText.text.toString()
                        .isNotEmpty()
                ) {
                    var catId = 0
                    expenseCategories.results.map {
                        if (it.name == categoryText.text.toString()) catId = it.id
                    }

                    viewModel.addExpense(
                        AgentExpensePost(
                            false,
                            expenseTypeText,
                            description,
                            name,
                            expenseInputCash.text.toString().toDouble(),
                            TokenSaver.getAgentId(),
                            catId
                        )
                    )
                } else requireContext().showToast("Ҳаражат номи,категория ёки изоҳ киритилмаган!")
            } else {
                requireContext().showToast("Тўлов миқдорини киритинг!")
            }

//            when {
//                expenseInputCard.text.toString().isNotEmpty() -> {
//
//                    if (name.isNotEmpty() && description.isNotEmpty()) {
//                        val d = AgentExpense(
//                            false,
//                            expenseTypeText,
//                            description,
//                            name,
//                            expenseInputCard.text.toString().toDouble(),
//                            TokenSaver.getAgentId()
//                        )
//                        //requireContext().showToast("$d")
//                        viewModel.addExpense(
//                            d
//                        )
//                    } else requireContext().showToast("Harajat nomi yoki izoh kiritilmagan!")
//
//
//                }
//                expenseInputCash.text.toString().isNotEmpty() -> {
//                    if (name.isNotEmpty() && description.isNotEmpty()) {
//                        val d = AgentExpense(
//                            false,
//                            expenseTypeText,
//                            description,
//                            name,
//                            expenseInputCash.text.toString().toDouble(),
//                            TokenSaver.getAgentId()
//                        )
////                        requireContext().showToast("$d")
//
//                        viewModel.addExpense(
//                            d
//                        )
//                    } else requireContext().showToast("Harajat nomi yoki izoh kiritilmagan!")
//                }
//                else -> {
//                    requireContext().showToast("To'lov miqdorini kiriting!")
//                }
//            }
        }


    }


//    private fun textChangedListener(editText: EditText) {
//
//        editText.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int,
//            ) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                setInputLogic()
//            }
//        })
//    }
//
//    private fun setInputLogic() {
//        expenseInputCard.isEnabled = expenseInputCash.text.toString().isEmpty()
//        expenseInputCash.isEnabled = expenseInputCard.text.toString().isEmpty()
//    }
//

    private val successBoxCountObserver = Observer<BoxCount> { countData ->
        boxCount.text = countData.quantity.toString()
    }


    private val successExpenseCatObserver = Observer<ExpenseCategories> { exp ->
        expenseCategories = exp
        if (expenseCategories.results.isNotEmpty()) categoryText.text =
            expenseCategories.results[0].name
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            salaryProgressBar.visibility = View.VISIBLE
        } else {
            salaryProgressBar.visibility = View.GONE
        }
    }

    private val errorSalaryObserver = Observer<String> {
        requireActivity().showToast(it)
        salaryProgressBar.visibility = View.GONE
    }


    private val timeOutExpenseObserver = Observer<Unit> {
        requireActivity().showToast("Интернет жуда паст!")
    }

    private val errorSalObserver = Observer<String> {
        requireActivity().showToast(it)
        salaryProgressBar.visibility = View.GONE
    }

    private val successHistoryObserver = Observer<SalaryData> { salaryData ->
        total_income_of_agents.text = salaryData.total_income_of_agents.toString()
        expense.text = salaryData.salary.toString()
        salary.text = salaryData.salary.toString()
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }

    private val successExpenseObserver = Observer<Any> {
        requireContext().showToast("Ҳаражат юборилди!")
        inputExpenseName.setText("")
        inputExpenseDescription.setText("")
        expenseInputCash.setText("")
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun soldHistorySetUp() {
        viewModel.progressSellLiveData.observe(this, progressObserver)
        viewModel.errorNotResponseLiveData.observe(this, errorSalObserver)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
        viewModel.successLiveData.observe(this, successHistoryObserver)
        viewModel.errorResponseLiveData.observe(this, errorSalaryObserver)
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun expenseAddSetUp() {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorAddExpenseLiveData.observe(this, errorSalObserver)
        viewModel.connectionErrorExpenseLiveData.observe(this, connectionErrorObserver)
        viewModel.successExpenseAddedLiveData.observe(this, successExpenseObserver)
        viewModel.errorTimeOutLiveDataAddClient.observe(this, timeOutExpenseObserver)
    }
}