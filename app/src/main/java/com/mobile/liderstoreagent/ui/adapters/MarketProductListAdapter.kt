package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.warehouse_product.Result
import com.mobile.liderstoreagent.utils.spannableText
import kotlinx.android.synthetic.main.market_item_products.view.*

class MarketProductListAdapter :
    ListAdapter<Result, MarketProductListAdapter.ViewHolder>(DiffItem) {

    var query = ""

    private var sellClickedListener: ((Result, Int) -> Unit)? = null
    private var returnClickedListener: ((Result) -> Unit)? = null

    object DiffItem : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.market_item_products, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val d = getItem(position)
            var dw =d.product.name
            dw = dw.toUpperCase()

            if (query != "") productName.text = dw spannableText query
            else productName.text = dw

            productPrice.text = d.product.incoming_price

            warehouseName.text = d.product.category.name

            productQuantity.text = d.quantity.toString() + " " + d.product.unit

            val day = d.product.updated_date.substring(0, 10)
            val count = d.product.updated_date.substring(11, 16)
            productDate.text = "$day $count"

            productSell.setOnClickListener {
                sellClickedListener?.invoke(d, position)
            }
            productDetail.setOnClickListener {
                returnClickedListener?.invoke(d)
            }

        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun clickedProduct(f: (Result, Int) -> Unit) {
        sellClickedListener = f
    }


    fun productDetailClicked(f: (Result) -> Unit) {
        returnClickedListener = f
    }

}