package com.mobile.liderstoreagent.ui.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import com.mobile.liderstoreagent.data.models.discountsmodel.Discounts
import com.mobile.liderstoreagent.ui.adapters.DiscountedProductListAdapter
import com.mobile.liderstoreagent.ui.dialogs.DiscountChooseDialog
import com.mobile.liderstoreagent.ui.viewmodels.DiscountsPageViewModel
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.discounts_fragment.*

@Suppress("DEPRECATION")
class DiscountsPage : Fragment(R.layout.discounts_fragment) {


    private val discountViewModel: DiscountsPageViewModel by viewModels()
    lateinit var discountedProducts: List<DiscountedProduct>
    lateinit var discountsAdapter: DiscountedProductListAdapter
    var chosenDiscount = 0
    var discounts: List<Discounts> = ArrayList()
    private var eventListener: ((Int) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discountedProducts = ArrayList<DiscountedProduct>()
        discountsSetUp()
        discountedProductsSetUp()



        searchByDiscount.setOnClickListener {
            if (discounts.isEmpty()) {
                requireActivity().showToast("Ҳали дата келмади!")
            } else {
                initDiscountsChooseDialog()
            }
        }


        refreshDiscounts.setOnRefreshListener {
           if(chosenDiscount!=0) discountViewModel.getDiscountedProducts(chosenDiscount)
            Handler().postDelayed(Runnable {
                refreshDiscounts.isRefreshing = false
            }, 2000)
        }



    }


    private fun discountsSetUp() {
        discountViewModel.progressDiscountsLiveData.observe(
            viewLifecycleOwner,
            progressDiscountedProductsObserver
        )
        discountViewModel.errorDiscountsLiveData.observe(viewLifecycleOwner, errorDiscountsObserver)
        discountViewModel.connectionErrorDiscountsLiveData.observe(
            viewLifecycleOwner,
            connectionErrorDiscountsObserver
        )
        discountViewModel.successDiscountsLiveData.observe(
            viewLifecycleOwner,
            successDiscountsObserver
        )
    }


    private val errorDiscountsObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
        discountsProgressBar.visibility = View.GONE
    }

    private val connectionErrorDiscountsObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }

    @SuppressLint("SetTextI18n")
    private val successDiscountsObserver = Observer<List<Discounts>> { discountsList ->
        discounts = discountsList

        if (!discounts.isEmpty()) {
            chosenDiscount = discounts[0].id
            discountViewModel.getDiscountedProducts(discounts[0].id)
            discountName.text = discounts[0].name
            discount.text = discounts[0].discount.toString() + " %"
            if (discounts[0].deadline.equals(null)) deadline.text = "Номаълум" else deadline.text =
                discounts[0].deadline.toString().substring(0,10)
        }
    }


    private fun initDiscountsChooseDialog() {
        val dialog = DiscountChooseDialog(requireContext(), discounts)
        dialog.show()
        dialog.setDiscountChosen { id1, name1, discount1, deadline1 ->
            discountName.text = name1
            deadline.text = deadline1
            discount.text = discount1
            discountViewModel.getDiscountedProducts(id1)
            chosenDiscount = id1
        }
    }

    private val progressDiscountedProductsObserver = Observer<Boolean> {
        if (it) {
            discountsProgressBar.visibility = View.VISIBLE
        } else {
            discountsProgressBar.visibility = View.GONE
        }
    }
    private val errorDiscountedProductsObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
        discountsProgressBar.visibility = View.GONE
    }
    private val connectionErrorDiscountedProductsObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }
    private val successDiscountedProductsObserver =
        Observer<List<DiscountedProduct>> { discountedProductList ->
            discountedProducts = discountedProductList

                initProductsList(discountedProducts)

        }

    private fun discountedProductsSetUp() {
        discountViewModel.progressDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            progressDiscountedProductsObserver
        )
        discountViewModel.errorDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            errorDiscountedProductsObserver
        )
        discountViewModel.connectionErrorDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            connectionErrorDiscountedProductsObserver
        )
        discountViewModel.successDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            successDiscountedProductsObserver
        )
    }


    private fun initProductsList(data: List<DiscountedProduct>) {
        discountsAdapter = DiscountedProductListAdapter()
        discountsAdapter.submitList(data)
        recyclerDiscountedProducts.layoutManager = LinearLayoutManager(requireContext())
        recyclerDiscountedProducts.adapter = discountsAdapter

        discountsAdapter.clickedProduct { id ->
            eventListener?.invoke(id)
        }
    }

    fun eventDiscountListener(f: (Int) -> Unit) {
        eventListener = f
    }
}