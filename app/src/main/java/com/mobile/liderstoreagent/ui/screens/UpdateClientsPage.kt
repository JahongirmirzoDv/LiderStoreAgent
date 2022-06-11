package com.mobile.liderstoreagent.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.clientmodel.clientdetail.ClientDetail
import com.mobile.liderstoreagent.data.models.clientmodel.MyTerritory
import com.mobile.liderstoreagent.data.models.clientmodel.Territory
import com.mobile.liderstoreagent.data.models.clientmodel.UpdateClientData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.AddClientSelectors
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.CarOption
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.MarketTypeOption
import com.mobile.liderstoreagent.data.models.clientmodel.pricetype.PriceType
import com.mobile.liderstoreagent.data.models.clientmodel.pricetype.PriceResult
import com.mobile.liderstoreagent.data.source.local.MyDatabase
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.local.selectors.CarsEntity
import com.mobile.liderstoreagent.data.source.local.selectors.MarketTypeEntity
import com.mobile.liderstoreagent.data.source.local.selectors.PriceTypeEntity
import com.mobile.liderstoreagent.data.source.local.selectors.TerritoryEntity
import com.mobile.liderstoreagent.ui.dialogs.SelectorDialog
import com.mobile.liderstoreagent.ui.viewmodels.AddClientViewModel
import com.mobile.liderstoreagent.utils.DatePickerHelper
import com.mobile.liderstoreagent.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.update_client_page.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class UpdateClientsPage : Fragment(R.layout.update_client_page) {

    var inputMarketName = ""
    var address = ""
    var carId= 0
    var repsonsiblePerson = ""
    var phone1 = ""
    var isValid = false
    var phone2 = ""
    lateinit var datePicker: DatePickerHelper
    var INN = ""
    var isDone = true
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    var imageAddress: File? = null


    var birthDateDirector = ""
    var carTxt = ""
    var marketTxt = ""
    var priceTxt = ""
    var territoryTxt = ""
    var territories = ArrayList<TerritoryEntity>()
    var workerTerritories = ArrayList<MyTerritory>()
    var clientId = 0
    lateinit var updateClientData: UpdateClientData
    lateinit var mySelectors: AddClientSelectors
    lateinit var myPriceType: PriceType
    private val viewModel: AddClientViewModel by viewModels()


    @SuppressLint("SetTextI18n", "FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.priceTypeUseCaseSuccess.observe(this, priceTypeUseCaseSuccessObserver)
        viewModel.getTerritories()

        val args = UpdateClientsPageArgs.fromBundle(requireArguments())
        clientId = args.myIdUpdate

        if (clientId > 0 && isDone) {
            isDone = false
            viewModel.getClientDetail(args.myIdUpdate)
        }
        else if (clientId == 0) {
            phoneNumber1.setText("+998")
            phoneNumber2.setText("+998")
        }


        if (birthDateDirector.isNotEmpty()) {
            datePickerDirector.setText(birthDateDirector)
        }

        if (carTxt.isNotEmpty()) mashinaType.text = carTxt
        if (territoryTxt.isNotEmpty()) territoryText.text = territoryTxt
        if (marketTxt.isNotEmpty()) marketType.text = marketTxt
        if (priceTxt.isNotEmpty()) priceTypeText.text = priceTxt

        viewModel.getSelectors()

        marketSpinner.setOnClickListener {

            var markets = ArrayList<String>()
            mySelectors.market_type_options.map { t -> markets.add(t.name) }

            val dialog = SelectorDialog(requireContext(), markets)
            dialog.setSelectedName { it ->
                marketType.text = it
                marketTxt = it
            }
            dialog.show()
        }


        priceType.setOnClickListener {

            var prices = ArrayList<String>()
            myPriceType.results.map { t -> prices.add(t.type) }

            val dialog = SelectorDialog(requireContext(), prices)
            dialog.setSelectedName { it ->
                priceTypeText.text = it
                priceTxt = it
            }
            dialog.show()
        }

        val db = MyDatabase.getDatabase(requireContext())
        GlobalScope.launch {
            territories = db.territoryDao().getAllTerritories() as ArrayList<TerritoryEntity>
            withContext(Dispatchers.Main){
                territories.map { t->
                    if(t.carId == carId) {
                        workerTerritories.add(MyTerritory(t.territoryId,t.territoryName))
                    }
                }
            }
        }

        carSpinner.setOnClickListener {

            var cars = ArrayList<String>()
            mySelectors.car_options.map { t -> cars.add(t.name) }

            val dialog = SelectorDialog(requireContext(), cars)
            dialog.setSelectedName { it ->
                territoryTxt = ""
                territoryText.text = ""
                mashinaType.text = it
                carTxt = it
                var id = 0
                mySelectors.car_options.map { t ->
                    if (carTxt == t.name) id = t.id
                }

                workerTerritories.clear()

                territories.map { t->
                    if(t.carId == id) {
                        workerTerritories.add(MyTerritory(t.territoryId,t.territoryName))
                    }
                }
            }
            dialog.show()
        }



        territorySpinner.setOnClickListener {

            if (workerTerritories.isNotEmpty()) {

                var territs = ArrayList<String>()
                workerTerritories.map { t -> territs.add(t.name) }

                val dialog = SelectorDialog(requireContext(), territs)
                dialog.setSelectedName { it ->
                    territoryText.text = it
                    territoryTxt = it
                }
                dialog.show()
            } else {
                requireActivity().showToast("Ҳудуд топилмади!")
            }
        }


        datePicker = DatePickerHelper(requireContext(), true)
        datePickerDirector.setOnClickListener {
            showDatePickerDialog()
        }

        setUpClientDetail()

        setUpSelectors()

        setUpUpdate()

        setUpTerritories()


        backToHomeAddClient.setOnClickListener {
            findNavController().navigateUp()
        }


        pickClientImage.setOnClickListener {
            ImagePicker.with(requireActivity())
                .saveDir(
                    File(
                        requireContext()
                            .getExternalFilesDir(null)?.let { it.absolutePath }, "DotanationBox"
                    )
                )
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            imageAddress = ImagePicker.getFile(data)!!
                            requireContext().showToast("Расм танланди")
                            selectedImageClient.setImageURI(Uri.fromFile(imageAddress))
                            selectedImageClient.visibility = View.VISIBLE
                            pickClientImage.text = "Расм олинди"

                        }
                        else -> {

                        }
                    }
                }
        }

        pickClientLocation.setOnClickListener {
            findNavController().navigate(R.id.action_updateClientsPage_to_mapsFragment)
        }

        sendClientData.setOnClickListener {
            inputMarketName = marketName.text.toString()
            address = clientAddress.text.toString()
            repsonsiblePerson = responsiblePerson.text.toString()
            phone1 = phoneNumber1.text.toString()
            phone2 = phoneNumber2.text.toString()
            INN = innNumber.text.toString()
            val direktorName = directorName.text.toString()

            var car = 0
            mySelectors.car_options.map { t ->
                if (t.name == mashinaType.text.toString()) {
                    car = t.id
                }
            }

            var market = 0
            mySelectors.market_type_options.map { t ->
                if (t.name == marketType.text.toString()) {
                    market = t.id
                }
            }


            var price = 0
            myPriceType.results.map { t ->
                if (t.type == priceTypeText.text.toString()) {
                    price = t.id
                }
            }

            var territory = 0
            if (workerTerritories.isNotEmpty()) {
                workerTerritories.map { t ->
                    if (t.name == territoryText.text.toString()) {
                        territory = t.id
                    }
                }
            }
            else  requireContext().showToast("Террриторилар бош")

                updateClientData = UpdateClientData(
                    clientId,
                    inputMarketName,
                    address,
                    repsonsiblePerson,
                    phoneNumber1.text.toString(),
                    phoneNumber2.text.toString(),
                    latitude,
                    longitude,
                    imageAddress,
                    TokenSaver.getAgentId(),
                    INN,
                    direktorName,
                    birthDateDirector,
                    car,
                    market,
                    mashrutKa.text.toString(),
                    territory,
                    price
                )
                if (clientId != 0) {
                    viewModel.updateClient(updateClientData)
                }
            }

    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpUpdate() {
        viewModel.locationLiveData.observe(viewLifecycleOwner, locationObserver)
        viewModel.progressUpdateLiveData.observe(this, progressObserver)
        viewModel.errorUpdateClientLiveData.observe(this, errorUpdateClientObserver)
        viewModel.connectionErrorUpdateLiveData.observe(this, connectionErrorObserver)
        viewModel.successUpdateLiveData.observe(this, successUpdateObserver)
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpSelectors() {
        viewModel.progressSelectorsLiveData.observe(this, progressObserver)
        viewModel.connectionErrorSelectorsLiveData.observe(this, connectionErrorObserver)
        viewModel.successSelectorsLiveData.observe(this, successSelectorsObserver)
        viewModel.errorTimeOutLiveDataSelectors.observe(this,errorSelectorsObserver)
        viewModel.errorSelectorsLiveData.observe(this,errorSelectorsObserver)
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpTerritories() {
        viewModel.progressTerritoryLiveData.observe(this, progressObserver)
        viewModel.connectionErrorTerritoryLiveData.observe(this, connectionErrorObserver)
        viewModel.successTerritoryLiveData.observe(this, successTerritoryObserver)
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpClientDetail() {
        viewModel.errorNotFoundLiveData.observe(this,errorClientNotFoundObserver)
        viewModel.progressClientDetailLiveData.observe(this, progressObserver)
        viewModel.connectionErrorClientDetailLiveData.observe(this, connectionErrorObserver)
        viewModel.successClientDetailLiveData.observe(this, successClientDetailObserver)
    }


    private fun showDatePickerDialog() {

        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)


        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                birthDateDirector = "${year}-${monthStr}-${dayStr}"
                datePickerDirector.setText(birthDateDirector)
            }
        })
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            clientAddProgressBar.visibility = View.VISIBLE
        } else {
            clientAddProgressBar.visibility = View.GONE
        }
    }
    private val errorUpdateClientObserver = Observer<String> {
       requireContext().showToast(it)
    }

    private val connectionErrorObserver = Observer<Unit> {
        setTimeOutAndConnectionError()
        requireActivity().showToast("Интернет юқ!")
    }

    private fun setTimeOutAndConnectionError(){

        val db = MyDatabase.getDatabase(requireContext())
        GlobalScope.launch {
            val cars = db.carsDao().getAllCars()
            val markets = db.marketTypeDao().getAllMarketTypes()
            val pricesType = db.priceTypeDao().getAllPriceTypes()
            territories = db.territoryDao().getAllTerritories() as ArrayList<TerritoryEntity>

            withContext(Dispatchers.Main) {
                var carsSel = ArrayList<CarOption>()
                var marketSel = ArrayList<MarketTypeOption>()
                var priceType = ArrayList<PriceResult>()


                pricesType.forEach {
                    priceType.add(PriceResult(it.priceId, it.type))
                }

                cars.forEach {
                    carsSel.add(CarOption(it.carId, it.name))
                }
                markets.forEach {
                    marketSel.add(MarketTypeOption(it.marketId, it.name))
                }


                myPriceType = PriceType(0,0,0,priceType)
                mySelectors = AddClientSelectors(carsSel, marketSel)



            }
        }

    }



    private val successUpdateObserver = Observer<Any> {
        if (it == true) {
            AlertDialog.Builder(requireContext())
                .setTitle("Диққат!")
                .setMessage("Диққат ҳаридор маълумотлари янгиланди!")
                .setPositiveButton("Ок") { dialog, _ ->
                    findNavController().navigateUp()
                    dialog.cancel()
                }.show()
        } else requireActivity().showToast("Хатолик!!! Маълумотларни тўғри киритинг!")
    }


    private val successSelectorsObserver = Observer<AddClientSelectors> { selectors ->
        mySelectors = AddClientSelectors(
            selectors.car_options,
            selectors.market_type_options,
        )

        val carsList = ArrayList<CarsEntity>()
        mySelectors.car_options.map { t -> carsList.add(CarsEntity(0, t.id, t.name)) }

        val marketList = ArrayList<MarketTypeEntity>()
        mySelectors.market_type_options.map { t ->
            marketList.add(
                MarketTypeEntity(
                    0,
                    t.id,
                    t.name
                )
            )
        }


        val db = MyDatabase.getDatabase(requireContext())
        GlobalScope.launch {
            db.carsDao().deleteAllCars()
            db.marketTypeDao().deleteAllMarketTypes()

            db.carsDao().insertCars(carsList)
            db.marketTypeDao().insertMarketTypes(marketList)
        }
    }


    private val priceTypeUseCaseSuccessObserver = Observer<PriceType> { priceType ->
        myPriceType = priceType
        val priceList = ArrayList<PriceTypeEntity>()
        myPriceType.results.map { t -> priceList.add(PriceTypeEntity(0, t.id, t.type)) }

        val db = MyDatabase.getDatabase(requireContext())
        GlobalScope.launch {
            db.priceTypeDao().deleteAllPriceTypes()

           db.priceTypeDao().insertPriceTypes(priceList)
        }

    }

    private val successTerritoryObserver = Observer<List<Territory>> { territoryList ->
        val territorySel = ArrayList<TerritoryEntity>()
        territoryList.map { t ->
            territorySel.add(TerritoryEntity(0,t.id,t.name,t.car.id)) }

        territories = territorySel

        workerTerritories.clear()
        territoryList.map { t->
            if(t.car.id == carId) {
                workerTerritories.add(MyTerritory(t.id,t.name))
            }
        }

        val db = MyDatabase.getDatabase(requireContext())
        GlobalScope.launch {
            db.territoryDao().deleteAllTerritories()

            db.territoryDao().insertTerritories(territorySel)
        }



    }

    private val successClientDetailObserver = Observer<ClientDetail> { clientDetail ->
        setUpInputs(clientDetail)
    }


    private val errorSelectorsObserver = Observer<Unit> {
        setTimeOutAndConnectionError()
        requireActivity().showToast("Интернет тезлиги жуда паст!")
        clientAddProgressBar.visibility = View.GONE
    }

    private fun setUpInputs(clientDetail: ClientDetail?) {

        marketName.setText(clientDetail!!.name)
        marketType.text = clientDetail.market_type.name
        priceTypeText.text = clientDetail.price_type.type
        clientAddress.setText(clientDetail.address)
        mashrutKa.setText(clientDetail.target)
        directorName.setText(clientDetail.director)
        datePickerDirector.setText(clientDetail.birthdate)
        phoneNumber1.setText(clientDetail.director_phone_number)
        responsiblePerson.setText(clientDetail.responsible_agent)
        phoneNumber2.setText(clientDetail.work_phone_number)
        innNumber.setText(clientDetail.INN)
        birthDateDirector = clientDetail.birthdate
        longitude = clientDetail.longitude.toDouble()
        latitude = clientDetail.latitude.toDouble()
        mashinaType.text = clientDetail.car.name
        territoryText.text = clientDetail.territory.name

        carTxt = clientDetail.car.name
        territoryTxt = clientDetail.territory.name
        marketTxt = clientDetail.market_type.name
        priceTxt = clientDetail.price_type.type

        carId = clientDetail.car.id






    }



    private val errorClientNotFoundObserver = Observer<String> {
        requireActivity().showToast(it)
    }



    private val locationObserver = Observer<String?> {

        requireContext().showToast(it)

        if (it != null) {
            val l = it.split(";")
            latitude = l[0].toDouble()//41
            longitude = l[1].toDouble()//69

            pickClientLocation.text = "Жой Янгиланди"
        }

    }
}