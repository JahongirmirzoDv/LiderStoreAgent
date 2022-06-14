package com.mobile.liderstoreagent.ui.pages

import android.app.Activity
import android.content.Intent
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
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.AddClientSelectors
import com.mobile.liderstoreagent.data.source.local.MyDatabase
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.ui.adapters.ClientListAdapter
import com.mobile.liderstoreagent.ui.dialogs.SelectorDialog
import com.mobile.liderstoreagent.ui.screens.HomeFragmentDirections
import com.mobile.liderstoreagent.ui.viewmodels.AddClientViewModel
import com.mobile.liderstoreagent.ui.viewmodels.ClientPageViewModel
import com.mobile.liderstoreagent.utils.log
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.clients_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime


@Suppress("DEPRECATION")
class ClientsPage : Fragment(R.layout.clients_fragment) {

    var printerText = ""
    lateinit var clientAdapter: ClientListAdapter
    lateinit var clientData: ClientsData
    private val viewModel: ClientPageViewModel by viewModels()

    var isAll = true
    lateinit var mySelectors: AddClientSelectors
    private val selectorViewModel: AddClientViewModel by viewModels()
    lateinit var recycler: RecyclerView
    var chosenClientType = ""
    var clientsData: List<ClientsData> = ArrayList()
    private var querySt = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.recyclerClients)


        selectorViewModel.getSelectors()
        selectorViewModel.successSelectorsLiveData.observe(
            viewLifecycleOwner,
            successSelectorsObserver
        )
        selectorViewModel.errorTimeOutLiveDataSelectors.observe(
            viewLifecycleOwner,
            errorTimeoutSelector
        )


        clientsSetUp()

        val closeButton = searchClientView.findViewById(R.id.search_close_btn) as ImageView

        closeButton.setOnClickListener {
            viewModel.closeSearch()
        }

        searchClients.setOnClickListener {
            if (this::mySelectors.isInitialized) {
                initClientsChooseDialog()
            } else {
                requireActivity().showToast("Ҳали маълумот келмади!")
            }
        }



        refreshClients.setOnRefreshListener {
            viewModel.getClients(chosenClientType)
            Handler().postDelayed(Runnable {
                refreshClients.isRefreshing = false
            }, 1000)
        }

        val handler = Handler()

        searchClientView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                if (query != null) {
                    querySt = query.trim()
                    initRecycler(clientsData.filter {
                        it.client.name.contains(
                            querySt,
                            true
                        ) || it.client.market_code.toString().contains(
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
                        initRecycler(clientsData.filter {
                            it.client.name.contains(
                                querySt,
                                true
                            ) || it.client.market_code.toString().contains(
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

    private val successSelectorsObserver = Observer<AddClientSelectors> { selectors ->
        mySelectors = AddClientSelectors(
            selectors.car_options,
            selectors.market_type_options,
        )
    }

    private val serverErrorObserver = Observer<String> { it ->
        clientsProgressBar.visibility = View.GONE
        requireActivity().showToast(it)
    }

    private val closeSearchObserver = Observer<Unit> {
        searchClientView.setQuery(null, false)
        searchClientView.clearFocus()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            clientsProgressBar.visibility = View.VISIBLE
        } else {
            clientsProgressBar.visibility = View.GONE
        }
    }
    private val errorClientsObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
        clientsProgressBar.visibility = View.GONE
    }
    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
        clientsProgressBar.visibility = View.GONE
    }
    private val successClientsObserver = Observer<List<ClientsData>> { list ->
        clientsData = list
        initRecycler(list)
        if (isAll) TokenSaver.setClientCount(list.size)
        isAll = false
    }

    private val errorTimeoutSelector = Observer<Unit> {
        clientsProgressBar.visibility = View.GONE
        requireActivity().showToast("Интернет ишламаяпди!")
    }

    fun initRecycler(data: List<ClientsData>) {
        clientAdapter = ClientListAdapter()
        clientAdapter.submitList(data)
        clientAdapter.query = querySt
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = clientAdapter

        clientAdapter.setOnClientChosen {
            findNavController().navigate(
                HomeFragmentDirections.actionMainFragmentToUpdateClientsPage(it)
            )
        }

        clientAdapter.setOnClientClick { it1, it2 ->
            findNavController().navigate(
                HomeFragmentDirections.actionMainFragmentToClientFragment(it1, it2.toFloat())
            )
        }

        clientAdapter.printClientDataClick { clientId ->

            var exist = false

            val db = MyDatabase.getDatabase(requireContext())
            GlobalScope.launch {
                if (db.printDao().exists(clientId)) exist = true
                val entity = db.printDao().findPrintByClientId(clientId)
                withContext(Dispatchers.Main) {
                    if (exist) {

                        clientsData.forEach {
                            if (it.client.id == clientId) {
                                clientData = it
                            }
                        }

                        if (::clientData.isInitialized) {
                            printerText = "SOFIN\n"
                            val currentDateTime = LocalDateTime.now()
                            printerText += "Дата: " + currentDateTime.dayOfMonth.toString() + ". " + currentDateTime.month.toString() + ". " + currentDateTime.year.toString() + ". " + currentDateTime.hour.toString() + "\n"
                            printerText += "Клиент: " + clientData.client.name + "\n"
                            printerText += "Аддрес: " + clientData.client.address + "\n"
                            printerText += "Продавец: " + TokenSaver.getFirstName() + "\n"
                            printerText += "Тел:" + TokenSaver.getLogin() + "\n"
                            printerText += "\nДолг: " + entity.initialDebt + "\n"
                            if (entity.sellData.isNotEmpty()) printerText += "Сотув\n" + entity.sellData + "\n"
                            if (entity.sellAmount != 0.0) printerText += "ИТОГО ПОКУПКА: " + entity.sellAmount + "\n"
                            if (entity.returnedProductData.isNotEmpty()) printerText += "ВОЗВРАТЫ\n" + entity.returnedProductData + "\n"
                            if (entity.returnedAmount != 0.0) printerText += "ИТОГО ВОЗВРАТ: " + entity.returnedAmount + "\n"
                            printerText += "ОПЛАТА: " + entity.payment + "\n"
                            printerText += "ДОЛГ: " + (entity.initialDebt + entity.sellAmount - entity.returnedAmount - entity.payment) + "\n"
                            printerText += "ПРИНЯЛ _____________ \n"
                            printerText += "SOFIN"
                        } else {
                            requireContext().showToast("Xato")
                        }

                        log(printerText, "PRINTER")

//                        startActivityForResult(
//                            Intent(
//                                requireContext(),
//                                ScanningActivity::class.java
//                            ), ScanningActivity.SCANNING_FOR_PRINTER

                    } else {
                        requireContext().showToast("Маълумот топилмади!")
                    }
                }
            }

        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK) {
//            var printables = ArrayList<Printable>()
//            var printable = TextPrintable.Builder()
//                .setText(printerText) //The text you want to print
//                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
//                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD) //Bold or normal
//                .setFontSize(DefaultPrinter.FONT_SIZE_NORMAL)
//                // .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON) // Underline on/off
//                .setCharacterCode(DefaultPrinter.CHARCODE_ARABIC_CP720) // Character code to support languages
//                .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
//                .setNewLinesAfter(1) // To provide n lines after sentence
//                .build()
//            printables.add(printable)
//            Printooth.printer().print(printables)
//        }
//    }


    private fun initClientsChooseDialog() {
        var cars = ArrayList<String>()
        mySelectors.car_options.map { t -> cars.add(t.name) }

        val dialog = SelectorDialog(requireContext(), cars)
        dialog.setSelectedName { filter ->
            chosenClientType = filter
            viewModel.getClients(filter)
        }
        dialog.show()
    }

    fun clientsSetUp() {
        viewModel.errorServerLiveData.observe(viewLifecycleOwner, serverErrorObserver)
        viewModel.closeLiveData.observe(viewLifecycleOwner, closeSearchObserver)

        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)

        viewModel.errorCategoriesLiveData.observe(viewLifecycleOwner, errorClientsObserver)
        viewModel.connectionErrorLiveData.observe(viewLifecycleOwner, connectionErrorObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successClientsObserver)
    }


}