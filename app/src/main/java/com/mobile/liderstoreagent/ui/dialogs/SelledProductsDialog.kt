package com.mobile.liderstoreagent.ui.dialogs

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.sellmodel.MarketSellData
import com.mobile.liderstoreagent.ui.adapters.SelledProductsListAdapter
import kotlinx.android.synthetic.main.discounts_dialog.view.recyclerDiscounts
import kotlinx.android.synthetic.main.selled_products_dialog.view.*


@SuppressLint("SetTextI18n", "NotifyDataSetChanged")
class SelledProductsDialog(
    context: Context,
    data: ArrayList<MarketSellData>,
    data1: ArrayList<ProductData>
) :
    BaseDialog(context, R.layout.selled_products_dialog) {
    private var listener: ((Int) -> Unit)? = null

    private var listener1: (() -> Unit)? = null
    private var adapter = SelledProductsListAdapter(data, data1)

    init {
        view.apply {
            recyclerDiscounts.adapter = adapter
            recyclerDiscounts.layoutManager = LinearLayoutManager(context)


            adapter.setOnDeleteChosen {
                listener?.invoke(it)
                adapter.notifyDataSetChanged()
                if (data.isEmpty()) {
                    close()
                }
            }


            var allPriced = 0.0
            data.forEachIndexed { i, marketSellData ->
                allPriced += (data[i].quantity) * (data[i].agent_sell_price * (1 - data[i].discount / 100.0))
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

