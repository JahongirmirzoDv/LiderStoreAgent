package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.utils.spannableText
import kotlinx.android.synthetic.main.market_item_products.view.*

class MarketProductListAdapter1 :
    ListAdapter<ProductData, MarketProductListAdapter1.ViewHolder>(DiffItem) {

    var query = ""

    private var sellClickedListener: ((ProductData) -> Unit)? = null
    private var returnClickedListener: ((ProductData) -> Unit)? = null

    object DiffItem : DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.market_item_products, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val d = getItem(position)

            var dw = d.code + " - " + d.name
            dw = dw.toUpperCase()

            if (query != "") productName.text = dw spannableText query
            else productName.text = dw

            productPrice.text = d.price.toString()

            warehouseName.text = d.warehouse_name

            productQuantity.text = d.quantity.toString() + " " + d.unit

            val day = d.last_update.substring(0, 10)
            val count = d.last_update.substring(11, 16)
            productDate.text = "$day $count"

            productSell.setOnClickListener {
                sellClickedListener?.invoke(d)
            }
            productDetail.setOnClickListener {
                returnClickedListener?.invoke(d)
            }

        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun clickedProduct(f: (ProductData) -> Unit) {
        sellClickedListener = f
    }


    fun productDetailClicked(f: (ProductData) -> Unit) {
        returnClickedListener = f
    }

}