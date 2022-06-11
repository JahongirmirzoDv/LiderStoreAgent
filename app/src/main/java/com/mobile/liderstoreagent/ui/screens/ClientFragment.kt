package com.mobile.liderstoreagent.ui.screens

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.InfoClass
import com.mobile.liderstoreagent.data.models.SellToClientOwnProduct
import com.mobile.liderstoreagent.data.models.categorymodel.CategoryData
import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.sellmodel.MarketSellData
import com.mobile.liderstoreagent.data.models.sellmodel.OwnMarketSellData
import com.mobile.liderstoreagent.data.models.sellmodel.SellOwnProduct
import com.mobile.liderstoreagent.data.models.warehouse.AgentData
import com.mobile.liderstoreagent.data.models.warehouse.WarehouseData
import com.mobile.liderstoreagent.data.models.warehouse_product.OwnProduct
import com.mobile.liderstoreagent.data.models.warehouse_product.Result
import com.mobile.liderstoreagent.data.source.local.MyDatabase
import com.mobile.liderstoreagent.data.source.local.prints.PrintEntity
import com.mobile.liderstoreagent.databinding.OwnProductSellDialogBinding
import com.mobile.liderstoreagent.databinding.ToSellClientDialogBinding
import com.mobile.liderstoreagent.ui.adapters.MarketProductListAdapter
import com.mobile.liderstoreagent.ui.adapters.MarketProductListAdapter1
import com.mobile.liderstoreagent.ui.dialogs.*
import com.mobile.liderstoreagent.ui.viewmodels.ProductsPageViewModel
import com.mobile.liderstoreagent.utils.SharedPref
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.client_page.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.mobile.liderstoreagent.ui.adapters.ToSellClientAdapter as ToSellClientAdapter1

class ClientFragment : Fragment(R.layout.client_page) {

    var clientId = ""
    var clientDebt = 0.0

    private val pageViewModel: ProductsPageViewModel by viewModels()
    lateinit var recycler: RecyclerView

    var chosenCategory = 0
    var chosenWarehouse = 0

    var marketSellData = ArrayList<MarketSellData>()
    var ownMarketSellData = ArrayList<OwnMarketSellData>()

    private lateinit var ownProductSell: List<SellOwnProduct>

    lateinit var categories: ArrayList<CategoryData>
    lateinit var warehouses: ArrayList<WarehouseData.Warehouse>
    lateinit var ownProductCategories: ArrayList<AgentData.CategoryModel>

//    private val productsAdapter by lazy { MarketProductListAdapter() }
    private val productsAdapter1 by lazy { MarketProductListAdapter1() }

    var productData: List<ProductData> = ArrayList()
    var ownProductData: List<Result> = ArrayList()

    var productSelledData: ArrayList<ProductData> = ArrayList()
    var productSelledData1: ArrayList<SellToClientOwnProduct> = ArrayList()
    var soldProductData: ArrayList<Result> = ArrayList()
    private var querySt = ""
    private var whichStore = -1

    var marketSellOwnOrFromWarehouse = -1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SharedPref.getInstanceDis(requireContext())
        val args = ClientFragmentArgs.fromBundle(requireArguments())
        clientId = args.myClientId.toString()
        clientDebt = args.clientDebt.toDouble()

        recycler = view.findViewById(R.id.recyclerProducts)

        categories = ArrayList()
        warehouses = ArrayList()
        ownProductCategories = ArrayList()

        pageViewModel.getOwnCategories()
//
        if (productData.isNotEmpty() && whichStore == 2) {
            initProductsList(productData)
            isShow()
        }

        if (ownProductData.isNotEmpty() && whichStore == 1) {
            initRecOwnProduct()
            isShow()
        }

        // categorySetUp()
        productsSetUp()
        warehouseSetUp()
        ownCategoriesSetUp()
        ownProductsSetUpData()

//        bottomNavigationViewClient.itemIconTintList = null
        bottomNavigationViewClient.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.clientReport -> {
                    findNavController().navigate(
                        ClientFragmentDirections.actionClientFragmentToClientReportFragment(
                            args.myClientId
                        )
                    )
                }

                R.id.clientShop -> {
                    findNavController().navigate(
                        ClientFragmentDirections.actionClientFragmentToClientInfoFragment(
                            args.myClientId
                        )
                    )
                }

                R.id.paymentClient -> {
                    findNavController().navigate(
                        ClientFragmentDirections.actionClientFragmentToPaymentFragment(
                            clientId.toInt(),
                            clientDebt.toFloat()
                        )
                    )
                }

                R.id.returnProduct -> {
                    val bundle = Bundle()
                    bundle.putInt("Int", clientId.toInt())
                    bundle.putFloat("Float", clientDebt.toFloat())
                    findNavController().navigate(R.id.homeReturnProductFragment, bundle)
                }

            }
            return@setOnNavigationItemSelectedListener true
        }

        refreshProducts.setOnRefreshListener {
            if (marketSellOwnOrFromWarehouse == 1) {

                pageViewModel.getProducts(chosenCategory, SharedPref.user ?: clientId, "", "")
                Handler().postDelayed(Runnable {
                    refreshProducts.isRefreshing = false
                }, 1000)
            }

            if (marketSellOwnOrFromWarehouse == 2) {
                pageViewModel.getOwnProducts(chosenCategory, clientId)
                Handler().postDelayed(Runnable {
                    refreshProducts.isRefreshing = false
                }, 1000)
            }
        }

        searchByWarehouse.setOnClickListener {
            whichStore = 2
            if (warehouses.isEmpty()) {
                pageViewModel.getWarehouse()
            } else {
                initWarehousesChooseDialog(warehouses)
            }
        }

        searchByOwnProduct.setOnClickListener {
            whichStore = 1
            if (ownProductCategories.isEmpty()) {
                pageViewModel.getOwnCategories()
            } else {
                initOwnProductsCategoryChooseDialog(ownProductCategories)
            }
        }

//        productsAdapter.clickedProduct { product, position ->
//
//            if (product.id == 0) {
//                requireContext().showToast("Бу маҳсулотга нарх белгиланмаган")
//            } else {
//
//                val builder = AlertDialog.Builder(requireContext())
//                val binding_dialog_own = OwnProductSellDialogBinding.inflate(layoutInflater)
//                builder.setView(binding_dialog_own.root)
//                val create = builder.create()
//                create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                binding_dialog_own.apply {
//
//                    sellCard.setOnClickListener {
//                        val _price_ = editPrice.text.toString()
//                        val _quantity_ = editQuantity.text.toString()
//
//                        if (_price_.isNotEmpty() && _quantity_.isNotEmpty()) {
//
//                            val price_ = _price_.toFloat()
//                            val quantity_ = _quantity_.toFloat()
//                            if (quantity_ < product.quantity.toFloat()) {
//                                productSelledData1.add(
//                                    SellToClientOwnProduct(
//                                        clientId.toInt(),
//                                        price_.toString(),
//                                        product.product.name,
//                                        product.id,
//                                        quantity_.toString(),
//                                        product.quantity
//                                    )
//                                )
//                                product.quantity = "${product.quantity.toFloat() - quantity_}"
//                                productsAdapter.notifyItemChanged(position)
//                                basketNotification.text =
//                                    (marketSellData.size + productSelledData1.size).toString()
//                                basketNotification.visibility = View.VISIBLE
//                                create.dismiss()
//                            } else {
//                                requireActivity().showToast("махсулот етарли эмас! Лимитдан ошган")
//                            }
//                        } else {
//                            create.dismiss()
//                        }
//                    }
//
//                }
//
//                create.show()
//
//            }
//        }


        productsAdapter1.clickedProduct { product ->

            if (product.price_id == 0) {
                requireContext().showToast("Бу маҳсулотга нарх белгиланмаган")
            } else {

                if (marketSellOwnOrFromWarehouse == 1) {
                    val dialog =
                        AmountInputDialog(requireContext(), product.price.toDouble())
                    dialog.setOnAmountInput { amount, sellPrice, discount ->
                        productSelledData.add(product)

                        if (amount < product.quantity.toDouble()) {
                            var dis = 0.0
                            if (discount != null) dis = discount
                            marketSellData.add(
                                MarketSellData(
                                    amount,
                                    clientId.toInt(),
                                    product.id,
                                    product.price_id,
                                    dis,
                                    product.warehouse,
                                    "ordered",
                                    sellPrice,
                                    product.category_discount.toDouble() + product.product_discount.toDouble()
                                )
                            )

                            basketNotification.text =
                                (marketSellData.size + productSelledData1.size).toString()
                            basketNotification.visibility = View.VISIBLE
                        } else {
                            requireContext().showToast("Омборда махсулот етарли эмас! Лимит: ${product.quantity}")
                        }
                    }
                    dialog.show()
                }
            }
        }

        //utadigan fragmentni kuriw kere
//        productsAdapter.productDetailClicked { id ->
//            val bundle = Bundle()
//            bundle.putSerializable(
//                "class",
//                InfoClass(
//                    id.product.name,
//                    id.quantity,
//                    "o`zidagi mahsulot",
//                    id.product.product_type,
//                    id.product.info.toString()
//                )
//            )
//            bundle.putInt("int", 1)
//            findNavController().navigate(R.id.productInfoFragment, bundle)
//        }

        productsAdapter1.productDetailClicked { id ->
            val bundle = Bundle()
            bundle.putSerializable(
                "class",
                InfoClass(id.name, id.quantity, id.warehouse_name, id.product_type, price = id.price)
            )
            bundle.putInt("int", 2)
            findNavController().navigate(R.id.productInfoFragment, bundle)
        }

        //buni omborni kurish kere
        shoppedProducts.setOnClickListener {
            if (marketSellData.isEmpty() && productSelledData1.isEmpty()) {
                requireContext().showToast("Ҳали сотмадингиз!")
            } else {

                if (marketSellData.isNotEmpty()) {
                    val dialog =
                        SelledProductsDialog(
                            requireContext(),
                            marketSellData,
                            productSelledData as ArrayList<ProductData>
                        )
                    dialog.setShopChosen {
                        pageViewModel.marketSell(marketSellData)
                        setDialog()
                    }
                    dialog.setDeletedProductChosen {
                        marketSellData.removeAt(it)
                        productSelledData.removeAt(it)
                        if (marketSellData.isEmpty()) {
                            marketSellData.clear()
                            isShow()
                        }
                    }
                    dialog.show()
                } else {
                    setDialog()
                }
            }
        }

        searchByInput.setOnClickListener {
            val text = searchInput.text.toString()

            if (text.isDigitsOnly()) {
                pageViewModel.getProducts(chosenCategory, SharedPref.user ?: clientId, searchInput.text.toString(), "")
            } else {
                pageViewModel.getProducts(chosenCategory, SharedPref.user ?: clientId, "", searchInput.text.toString())
            }

        }

    }

    private fun setDialog() {
        if (productSelledData1.isNotEmpty()) {
            val adapter = ToSellClientAdapter1(productSelledData1)
            val builder = AlertDialog.Builder(requireContext())
            val inflate = ToSellClientDialogBinding.inflate(layoutInflater)
            val create = builder.create()
            create.setView(inflate.root)
            create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            inflate.apply {

                recycle.adapter = adapter

                cardOk.setOnClickListener {
                    pageViewModel.ownMarketSell(productSelledData1)
                    create.dismiss()
                }

                adapter.clickedD {
                    productSelledData1.removeAt(it)
                    adapter.notifyItemRemoved(it)
                    isShow()
                    if (productSelledData1.isEmpty()) {
                        productSelledData1.clear()
                        create.dismiss()
                    }
                }

                adapter.clickedE { sellToClientOwnProduct, i ->
                    val builder = AlertDialog.Builder(requireContext())
                    val binding_dialog_own = OwnProductSellDialogBinding.inflate(layoutInflater)
                    builder.setView(binding_dialog_own.root)
                    val create1 = builder.create()
                    create1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    binding_dialog_own.apply {
                        editPrice.setText(sellToClientOwnProduct.price)
                        editQuantity.setText(sellToClientOwnProduct.quantity)

                        sellCard.setOnClickListener {
                            val _price_ = editPrice.text.toString()
                            val _quantity_ = editQuantity.text.toString()

                            if (_price_.isNotEmpty() && _quantity_.isNotEmpty()) {

                                val price_ = _price_.toFloat()
                                val quantity_ = _quantity_.toFloat()
                                if (quantity_ < sellToClientOwnProduct.quantityAll.toFloat()) {
                                    sellToClientOwnProduct.quantity = quantity_.toString()
                                    sellToClientOwnProduct.price = price_.toString()
                                    sellToClientOwnProduct.quantityAll =
                                        "${sellToClientOwnProduct.quantityAll.toFloat() - quantity_}"
                                    adapter.notifyItemChanged(i)

                                    create1.dismiss()
                                } else {
                                    requireActivity().showToast("махсулот етарли эмас! Лимитдан ошган")
                                }
                            } else {
                                create1.dismiss()
                            }
                        }

                    }

                    create1.show()
                }
            }
            create.show()
        }
    }

    fun isShow() {
        val i = marketSellData.size + productSelledData1.size
        if (i > 0) {
            basketNotification.text = i.toString()
            basketNotification.visibility = View.VISIBLE
        } else {
            basketNotification.visibility = View.GONE
        }
    }

    private fun ownCategoriesSetUp() {
        pageViewModel.errorOwnCategoriesLiveData.observe(
            viewLifecycleOwner,
            errorCategoriesObserver
        )
        pageViewModel.progressOwnCategoriesLiveData.observe(viewLifecycleOwner, progressObserver)

        pageViewModel.connectionErrorOwnCategoriesLiveData.observe(
            viewLifecycleOwner,
            connectionErrorObserver
        )

        pageViewModel.successOwnCategoriesLiveData.observe(
            viewLifecycleOwner,
            successOwnCategoriesObserver
        )
    }

    private val progressObserver = Observer<Boolean> {
        productsProgressBar.isVisible = it
    }

    private val errorCategoriesObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
        productsProgressBar.visibility = View.GONE
    }

    private val successCategoriesObserver = Observer<List<CategoryData>> { category ->
        categories = category as ArrayList<CategoryData>
        chosenCategory = categories[0].id
        pageViewModel.getProducts(categories[0].id, SharedPref.user ?: clientId, "", "")
    }

    private val successWarehousesObserver = Observer<WarehouseData> { warehouse ->
        warehouses = warehouse.results as ArrayList<WarehouseData.Warehouse>
        chosenWarehouse = warehouses[0].id
    }

    private val successOwnCategoriesObserver = Observer<AgentData> { ownData ->
        ownProductCategories = ownData.categories as ArrayList<AgentData.CategoryModel>
    }

    private val successMarketSellObserver = Observer<Any> { any ->
        var sellData = ""
        var totalPrice = 0.0
        marketSellData.forEach { element ->

            var unit = ""
            var price = ""

            productData.forEach {
                if (it.id == element.product) {
                    unit = it.unit
                    price = it.price
                    sellData += it.name
                }
            }

            sellData += "  " + element.quantity + " $unit x " + "$price" + " = ${element.quantity * price.toDouble()}\n"
            totalPrice += element.quantity * price.toDouble()

        }

        val db = MyDatabase.getDatabase(requireContext())
        GlobalScope.launch {
            if (db.printDao().exists(clientId.toInt())) {
                db.printDao().deletePrintByClientId(clientId.toInt())
//              val entity = db.printDao().findPrintByClientId(clientId.toInt())
//               db.printDao().updatePrint(
//                   PrintEntity(
//                       entity.id,
//                       entity.clientId,
//                       clientDebt,
//                       entity.sellData + sellData,
//                       entity.sellAmount + totalPrice,
//                       entity.returnedProductData,
//                       entity.returnedAmount,
//                       0.0
//                   )
//               )
                db.printDao().insertPrint(
                    PrintEntity(
                        0,
                        clientId.toInt(),
                        clientDebt,
                        sellData,
                        totalPrice,
                        "",
                        0.0,
                        0.0
                    )
                )
            } else {
                db.printDao().insertPrint(
                    PrintEntity(
                        0,
                        clientId.toInt(),
                        clientDebt,
                        sellData,
                        totalPrice,
                        "",
                        0.0,
                        0.0
                    )
                )
            }

            // withContext(Dispatchers.Main){}
        }

        marketSellData.clear()
        requireContext().showToast("Маҳсулотлар сотилди!")
        isShow()
    }

    private val successOwnMarketSellObserver = Observer<Any> { any ->
        var sellData = ""
        var totalPrice = 0.0
        ownMarketSellData.forEach { element ->

            var unit = ""
            var price = ""

            ownProductData.forEach {
                if (it.id == element.product) {
                    unit = it.product.unit
                    price = it.product.incoming_price
                    sellData += it.product.name
                }
            }

            sellData += "  " + element.quantity + " $unit x " + "$price" + " = ${element.quantity * price.toDouble()}\n"
            totalPrice += element.quantity * price.toDouble()

        }

        val db = MyDatabase.getDatabase(requireContext())
        GlobalScope.launch {
            if (db.printDao().exists(clientId.toInt())) {
                db.printDao().deletePrintByClientId(clientId.toInt())

//              val entity = db.printDao().findPrintByClientId(clientId.toInt())
//               db.printDao().updatePrint(
//                   PrintEntity(
//                       entity.id,
//                       entity.clientId,
//                       clientDebt,
//                       entity.sellData + sellData,
//                       entity.sellAmount + totalPrice,
//                       entity.returnedProductData,
//                       entity.returnedAmount,
//                       0.0
//                   )
//               )
                db.printDao().insertPrint(
                    PrintEntity(
                        0,
                        clientId.toInt(),
                        clientDebt,
                        sellData,
                        totalPrice,
                        "",
                        0.0,
                        0.0
                    )
                )
            } else {
                db.printDao().insertPrint(
                    PrintEntity(
                        0,
                        clientId.toInt(),
                        clientDebt,
                        sellData,
                        totalPrice,
                        "",
                        0.0,
                        0.0
                    )
                )
            }

            // withContext(Dispatchers.Main){}
        }

        marketSellData.clear()
        productSelledData1.clear()
        basketNotification.visibility = View.GONE
        requireContext().showToast("Маҳсулотлар сотилди!")
    }


//hesitate

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun categorySetUp() {
//        pageViewModel.progressCategoriesLiveData.observe(this, progressObserver)
//        pageViewModel.errorCategoriesLiveData.observe(this, errorCategoriesObserver)
//        pageViewModel.connectionErrorCategoriesLiveData.observe(
//            this,
//            connectionErrorObserver
//        )
//        pageViewModel.successCategoriesLiveData.observe(
//            this,
//            successCategoriesObserver
//        )
    }

    private fun warehouseSetUp() {

        pageViewModel.errorWarehouseLiveData.observe(viewLifecycleOwner, errorCategoriesObserver)
        pageViewModel.progressWarehouseLiveData.observe(viewLifecycleOwner, progressObserver)

        pageViewModel.connectionErrorWarehouseLiveData.observe(
            viewLifecycleOwner,
            connectionErrorObserver
        )

        pageViewModel.successWarehouseLiveData.observe(
            viewLifecycleOwner,
            successWarehousesObserver
        )

    }

    private val errorProductsObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
    }

    private val errorNoteEnoughProduct = Observer<String> {
        productsProgressBar.visibility = View.GONE
        requireActivity().showToast(it)
    }

    private val successProductsObserver = Observer<List<ProductData>> { products ->
        productData = products
        initProductsList(products)

        if (products.isEmpty()) {
            requireContext().showToast("Маҳсулот топилмади")
        } else {
            searchInput.setText("")
        }
    }


    private val successOwnProductsObserver = Observer<OwnProduct> { products ->
        ownProductData = products.results
        initRecOwnProduct()

        if (products.results.isEmpty()) {
            requireContext().showToast("Маҳсулот топилмади")
        } else {
            searchInput.setText("")
        }
    }

    private fun initRecOwnProduct() {
//        productsAdapter.submitList(ownProductData)
//        productsAdapter.query = querySt
        recycler.layoutManager = LinearLayoutManager(requireContext())
//        recycler.adapter = productsAdapter
    }


    @SuppressLint("FragmentLiveDataObserve")
    fun productsSetUp() {
        pageViewModel.progressProductsLiveData.observe(this, progressObserver)
        pageViewModel.errorProductsLiveData.observe(this, errorProductsObserver)
        pageViewModel.successProductsLiveData.observe(this, successProductsObserver)

        pageViewModel.successMarketSellLiveData.observe(
            this,
            successMarketSellObserver
        )
        pageViewModel.progressMarketSellLiveData.observe(this, progressObserver)
        pageViewModel.errorMarketSellLiveData.observe(this, errorProductsObserver)
        pageViewModel.errorNotEnoughProduct.observe(this, errorNoteEnoughProduct)

    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun ownProductsSetUpData() {
        pageViewModel.progressOwnProductsLiveData.observe(this, progressObserver)
        pageViewModel.errorOwnProductsLiveData.observe(this, errorProductsObserver)
        pageViewModel.successOwnProductsLiveData.observe(this, successOwnProductsObserver)

        pageViewModel.successOwnMarketSellLiveData.observe(
            this,
            successOwnMarketSellObserver
        )
        pageViewModel.progressOwnMarketSellLiveData.observe(this, progressObserver)
        pageViewModel.errorOwnMarketSellLiveData.observe(this, errorProductsObserver)
        pageViewModel.errorOwnNotEnoughProduct.observe(this, errorNoteEnoughProduct)

    }


    private fun initProductsList(data: List<ProductData>) {
        productsAdapter1.submitList(data)
        productsAdapter1.query = querySt
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = productsAdapter1
    }

    private fun initCategoryChooseDialog(data: List<WarehouseData.Warehouse.CategoryModel>) {
        val dialog = CategoryChooseDialog(requireContext(), data)
        dialog.show()
        marketSellOwnOrFromWarehouse = 1
        dialog.setOnCategoryChosen { id ->
            chosenCategory = id
            pageViewModel.getProducts(id, SharedPref.user ?: clientId, "", "")
        }
    }

    private fun initOwnProductsCategoryChooseDialog(data: List<AgentData.CategoryModel>) {
        val dialog = OwnProductsCategoryChooseDialog(requireContext(), data)
        dialog.show()
        marketSellOwnOrFromWarehouse = 2
        dialog.setOnCategoryChosen { id ->
            chosenCategory = id
            pageViewModel.getOwnProducts(id, clientId)
        }
    }

    private fun initWarehousesChooseDialog(data: List<WarehouseData.Warehouse>) {
        val dialog = WarehouseChooseDialog(requireContext(), data)
        dialog.show()

        dialog.setOnWarehouseChosen { position ->
            chosenWarehouse = position
            if (data[position].categories.isNotEmpty()) {
                initCategoryChooseDialog(data[position].categories)
            } else {
                dialog.close()
                Toast.makeText(
                    requireContext(),
                    "${data[position].name} омборда категориялар рўйхати бўш",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}