package com.mobile.liderstoreagent.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.clientmodel.AddClientData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.MarketCode
import com.mobile.liderstoreagent.data.source.local.MyDatabase
import com.mobile.liderstoreagent.data.source.local.clients.ClientEntity
import com.mobile.liderstoreagent.ui.adapters.SavedClientsAdapter
import com.mobile.liderstoreagent.ui.viewmodels.AddClientViewModel
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.saved_clients.*
import kotlinx.android.synthetic.main.saved_clients.clientAddProgressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class SavedClients : Fragment(R.layout.saved_clients) {
    private val viewModel: AddClientViewModel by viewModels()
    lateinit var myClients: ArrayList<ClientEntity>
    lateinit var savedAdapter: SavedClientsAdapter
    var delation = -1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAddClient()
        val db = MyDatabase.getDatabase(requireContext())

        iv_back.setOnClickListener {
            findNavController().navigateUp()
        }

        GlobalScope.launch {
            myClients = db.clientDao().getAll() as ArrayList<ClientEntity>

            withContext(Dispatchers.Main) {
                initRecycler(myClients)
            }
        }
    }

    private fun initRecycler(myClients: ArrayList<ClientEntity>) {
        if(myClients.isEmpty()) requireContext().showToast("Сақланганлар листи бўш!")
        savedAdapter = SavedClientsAdapter(myClients)
        savedClientsRecycler.adapter = savedAdapter
        savedClientsRecycler.layoutManager = LinearLayoutManager(context)

        savedAdapter.setOnDelete {
            val db = MyDatabase.getDatabase(requireContext())
            val currentClient = myClients[it]
            GlobalScope.launch {
                db.clientDao().deleteClient(currentClient)
            }
            myClients.removeAt(it)
            savedAdapter.notifyDataSetChanged()
        }

        savedAdapter.setOnAdd {
            delation = it
            viewModel.addClient(
                AddClientData(
                    0,
                    myClients[it].marketName,
                    myClients[it].address,
                    myClients[it].responsiblePerson,
                    myClients[it].directorPhone,
                    myClients[it].workPhone,
                    myClients[it].latitude,
                    myClients[it].longitude,
                    File(myClients[it].image),
                    myClients[it].agentId,
                    myClients[it].INN,
                    myClients[it].directorName,
                    myClients[it].birthDate,
                    myClients[it].car,
                    myClients[it].market,
                    myClients[it].target,
                    myClients[it].territory,
                    myClients[it].price_type
                )
            )
        }

    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpAddClient() {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorAddClientLiveData.observe(this, errorAddClientObserver)
        viewModel.errorAddClientServerLiveData.observe(this, errorAddClientServerObserver)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.errorTimeOutLiveDataAddClient.observe(this,errorTimeOutAddClientObserver)
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            clientAddProgressBar.visibility = View.VISIBLE
        } else {
            clientAddProgressBar.visibility = View.GONE
        }
    }

    private val errorAddClientObserver = Observer<String> {
        requireActivity().showToast("Хатолик!")
    }

    private val errorAddClientServerObserver = Observer<String> {
        clientAddProgressBar.visibility = View.GONE
        requireActivity().showToast(it)
    }

    private val successObserver = Observer<MarketCode> {
        val currentClient = myClients[delation]
        val db = MyDatabase.getDatabase(requireContext())
        GlobalScope.launch {
            db.clientDao().deleteClient(currentClient)
        }
        myClients.removeAt(delation)
        savedAdapter.notifyDataSetChanged()

        if (it.market_code.isNotEmpty()) {
            AlertDialog.Builder(requireContext())
                .setTitle("Диққат!")
                .setMessage("Диққат ҳаридор маълумотлари юборилди! Дўкон коди:${it.market_code}")
                .setPositiveButton("Ok") { dialog, _ ->
                    findNavController().navigateUp()
                    dialog.cancel()
                }.show()
        }
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }

    private val errorTimeOutAddClientObserver = Observer<Unit> {
        clientAddProgressBar.visibility = View.GONE
        requireActivity().showToast("Интернет тезлиги жуда паст!")
    }

}