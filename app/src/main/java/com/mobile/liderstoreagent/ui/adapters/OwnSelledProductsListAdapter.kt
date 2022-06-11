package com.mobile.liderstoreagent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.sellmodel.OwnMarketSellData
import kotlinx.android.synthetic.main.selled_products.view.*

class OwnSelledProductsListAdapter(val data: ArrayList<OwnMarketSellData>, val data1: ArrayList<ProductData>,) :
    RecyclerView.Adapter<OwnSelledProductsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.selled_products, parent, false
        )
    )

    private var listener: ((Int) -> Unit)? = null

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.apply {
                productDelete.setOnClickListener {
                  listener?.invoke(adapterPosition)
                }
            }
        }


        @SuppressLint("SetTextI18n")
        fun bind() {
            val d = data[adapterPosition]
            val d1 = data1[adapterPosition]
            itemView.apply {
                productName.text=(d1.code+"-"+d1.name).toUpperCase()
                productPrice.text = "${d.quantity} ${d1.unit} x ${d1.price} = ${d.quantity*d1.price.toDouble()}"
                if(d.discount!=0.0) {
                    discountPrice.visibility = View.VISIBLE
                    discountPrice.text = "${d.discount} ${d1.unit} x ${d1.price} = 0"
                }

            }
        }
    }

    fun setOnDeleteChosen(f: ((Int) -> Unit)?) {
        listener = f
    }

}
