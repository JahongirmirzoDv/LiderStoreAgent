package com.mobile.liderstoreagent.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.clientmodel.clientdetail.ClientDetail
import com.mobile.liderstoreagent.ui.viewmodels.AddClientViewModel
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.client_info_fragment.*

class ClientInfoFragment : Fragment(R.layout.client_info_fragment) {

    private val viewModel: AddClientViewModel by viewModels()
    var myClientId = 0
    lateinit var myClientDetail: ClientDetail


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = ClientInfoFragmentArgs.fromBundle(requireArguments())
        myClientId = args.clientIdForInfo
        if (myClientId != 0) viewModel.getClientDetail(myClientId)
        setUpClientDetail()

        clientOrientation.setOnClickListener {
            if (this::myClientDetail.isInitialized) {
                val gmmIntentUri =
                    Uri.parse("http://maps.google.com/maps?saddr=${myClientDetail.latitude},${myClientDetail.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            } else requireContext().showToast("Маълумот топилмади")
        }

    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            clientInfoProgressBar.visibility = View.VISIBLE
        } else {
            clientInfoProgressBar.visibility = View.GONE
        }
    }


    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }


    private val successClientDetailObserver = Observer<ClientDetail> { clientDetail ->
        setUpClientData(clientDetail)
        myClientDetail = clientDetail
    }

    //


    private fun setUpClientData(clientDetail: ClientDetail?) {
        clientMarketType.text = clientDetail!!.market_type.name
        clientName.text = clientDetail.name
        clientCode.text = clientDetail.market_code.toString()
        clientAddress.text = clientDetail.address
        clientDirector.text = clientDetail.director
        clientDirectorBirthdate.text = clientDetail.birthdate
        clientDirectorPhone.text = clientDetail.director_phone_number
        clientResponsiblePerson.text = clientDetail.responsible_agent
        clientWorkPhone.text = clientDetail.work_phone_number
        clientTarget.text = clientDetail.target
        clientTerritory.text = clientDetail.territory.name
        clientCar.text = clientDetail.car.name
        clientPrice.text = clientDetail.price_type.type

        Glide.with(client_image.context).load("${clientDetail.image}")
            .placeholder(R.drawable.ic_baseline_image_not_supported_24)
            .into(client_image)
    }


    private val errorClientNotFoundObserver = Observer<String> {
        requireActivity().showToast(it)
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpClientDetail() {
        viewModel.errorNotFoundLiveData.observe(this,errorClientNotFoundObserver)
        viewModel.progressClientDetailLiveData.observe(this, progressObserver)
        viewModel.connectionErrorClientDetailLiveData.observe(this, connectionErrorObserver)
        viewModel.successClientDetailLiveData.observe(this, successClientDetailObserver)
    }

}