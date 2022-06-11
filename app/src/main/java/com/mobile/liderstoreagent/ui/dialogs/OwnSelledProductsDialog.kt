package com.mobile.liderstoreagent.ui.dialogs

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.sellmodel.OwnMarketSellData
import com.mobile.liderstoreagent.ui.adapters.OwnSelledProductsListAdapter
import kotlinx.android.synthetic.main.discounts_dialog.view.recyclerDiscounts
import kotlinx.android.synthetic.main.selled_products_dialog.view.*


@SuppressLint("SetTextI18n")
class OwnSelledProductsDialog(
    context: Context,
    data: ArrayList<OwnMarketSellData>,
    data1: ArrayList<ProductData>
) :
    BaseDialog(context, R.layout.selled_products_dialog) {
    private var listener: ((Int) -> Unit)? = null

    private var listener1: (() -> Unit)? = null
    private var adapter = OwnSelledProductsListAdapter(data, data1)

    init {
        view.apply {
            recyclerDiscounts.adapter = adapter
            recyclerDiscounts.layoutManager = LinearLayoutManager(context)


            adapter.setOnDeleteChosen {
                listener!!.invoke(it)
                data.removeAt(it)
                data1.removeAt(it)
                adapter.notifyDataSetChanged()
            }


            var allPriced = 0.0
            for(i in 0 until data.size){
                allPriced += (data[i].quantity)*(data1[i].price.toDouble())
            }

            allPrice.text = "Жами: $allPriced"


            sellProduct.setOnClickListener {
                listener1!!.invoke()
                close()
            }


        }
    }

    fun setDeletedProductChosen(f: ((Int) -> Unit)?) {
        listener = f
    }

    fun setShopChosen(f: (() -> Unit)?) {
        listener1 = f
    }
}

