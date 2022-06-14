package com.mobile.liderstoreagent.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.ClientProducts
import com.mobile.liderstoreagent.data.models.payment.PaymentData
import com.mobile.liderstoreagent.data.source.local.MyDatabase
import com.mobile.liderstoreagent.data.source.local.prints.PrintEntity
import com.mobile.liderstoreagent.ui.viewmodels.ClientProductsViewModel
import com.mobile.liderstoreagent.ui.viewmodels.PaymentViewModel
import com.mobile.liderstoreagent.utils.hideKeyboard
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.payment_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PaymentFragment : Fragment(R.layout.payment_fragment) {
    private val paymentViewModel: PaymentViewModel by viewModels()

    private val clientProductsViewModel: ClientProductsViewModel by viewModels()
    var productData: List<ClientProducts> = ArrayList()

    var clientId = 0
    var clientDebt = 0.0
    var paymentAmount = 0.0
    var paymentType = "sum"
    var paymentProductId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = PaymentFragmentArgs.fromBundle(requireArguments())
        clientId = args.clientId
        clientDebt = args.clientDebt.toDouble()


        paymentSetUp()

        clientProductsSetUp()

        clientProductsViewModel.getClientProducts(clientId)


        backToClientPage.setOnClickListener {
            inputCash.hideKeyboard(requireActivity())
            inputCommentPayment.hideKeyboard(requireActivity())
            findNavController().navigateUp()
        }

        money_type.setOnCheckedChangeListener { radioGroup, chackedID ->
            when (chackedID) {
                R.id.dollar_radio_btn -> {
                    paymentType = "dollar"
                    valuta_usd_edit.visibility = View.GONE
                    valuta_usd.visibility = View.GONE
                }
                R.id.sum_radio_btn -> {
                    paymentType = "sum"
                    valuta_usd_edit.visibility = View.VISIBLE
                    valuta_usd.visibility = View.VISIBLE
                }
            }
        }

        money_type.check(R.id.dollar_radio_btn)

        spinner_money_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (position) {
                    0 -> {
                        money_type.visibility = View.VISIBLE
                    }
                    1 -> {
                        money_type.visibility = View.GONE
                        paymentType = "karta"
                    }
                    2 -> {
                        money_type.visibility = View.GONE
                        paymentType = "kvitansiya"
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        chb_by_product.setOnCheckedChangeListener { buttonView, isChecked ->
            layout_spinner_products.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        inputCash.doOnTextChanged { text, start, before, count ->
            inputCash.error = null
        }

        payPayment.setOnClickListener {
            inputCash.hideKeyboard(requireActivity())
            inputCommentPayment.hideKeyboard(requireActivity())

            val cashText = inputCash.text.toString()
            when {
                cashText.isNotEmpty() || cashText.toDouble() != 0.0 -> {

                    val productId: Int? = if (chb_by_product.isChecked) paymentProductId else null
                    paymentAmount = cashText.toDouble()
                    paymentViewModel.payPayment(
                        PaymentData(
                            clientId,
                            paymentType,
                            paymentAmount,
                            productId,
                            inputCommentPayment.text.toString()
                        )
                    )
                }
                else -> {
                    inputCash.error = "Тўлов миқдорини киритинг!"
//                    requireContext().showToast("Тўлов миқдорини киритинг!")
                }
            }
        }

    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            paymentProgressBar.visibility = View.VISIBLE
        } else {
            paymentProgressBar.visibility = View.GONE
        }
    }
    private val errorPaymentObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
        paymentProgressBar.visibility = View.GONE

    }
    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }
    private val successPaymentObserver = Observer<Any> { any ->

        val db = MyDatabase.getDatabase(requireContext())
        GlobalScope.launch {
            if (db.printDao().exists(clientId)) {
                val entity = db.printDao().findPrintByClientId(clientId)
                db.printDao().updatePrint(
                    PrintEntity(
                        entity.id,
                        entity.clientId,
                        entity.initialDebt,
                        entity.sellData,
                        entity.sellAmount,
                        entity.returnedProductData,
                        entity.returnedAmount,
                        entity.payment + paymentAmount
                    )
                )
            } else {
                db.printDao().insertPrint(
                    PrintEntity(
                        0,
                        clientId,
                        clientDebt,
                        "",
                        0.0,
                        "",
                        0.0,
                        paymentAmount
                    )
                )
            }
        }

        inputCash.setText("")
        inputCommentPayment.setText("")
        requireActivity().showToast("Тўлов юборилди!")

    }

    private fun paymentSetUp() {
        paymentViewModel.progressPaymentData.observe(viewLifecycleOwner, progressObserver)
        paymentViewModel.errorPaymentLiveData.observe(viewLifecycleOwner, errorPaymentObserver)
        paymentViewModel.connectionErrorLiveData.observe(
            viewLifecycleOwner,
            connectionErrorObserver
        )
        paymentViewModel.successPaymentLiveData.observe(viewLifecycleOwner, successPaymentObserver)
    }

    private fun clientProductsSetUp() {
        clientProductsViewModel.progressProductsLiveData.observe(
            viewLifecycleOwner,
            progressObserver
        )
        clientProductsViewModel.errorProductsLiveData.observe(
            viewLifecycleOwner,
            errorPaymentObserver
        )
        clientProductsViewModel.successProductsLiveData.observe(
            viewLifecycleOwner,
            successProductsObserver
        )
        clientProductsViewModel.connectionErrorProductsLiveData.observe(
            viewLifecycleOwner,
            connectionErrorObserver
        )
    }

    private val successProductsObserver = Observer<List<ClientProducts>> { products ->
        productData = products
        if (products.isNotEmpty()) {
            initProductsListSpinner(products)
            chb_by_product.visibility = View.VISIBLE
        } else {
            chb_by_product.visibility = View.GONE
        }

    }

    private fun initProductsListSpinner(data: List<ClientProducts>) {

        val list = mutableListOf<String>()
        for (item in data) {
            list.add(item.product.name)
        }

        // access the spinner
        if (spinner_products != null) {
            val adapter = ArrayAdapter(
                requireContext(), R.layout.item_spinner_client_products, list
            )
            spinner_products.adapter = adapter

            spinner_products.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    paymentProductId = data[position].product.id
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

}