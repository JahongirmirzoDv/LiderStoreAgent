package com.mobile.liderstoreagent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.sellmodel.MarketSellData
import kotlinx.android.synthetic.main.selled_products.view.*

class SelledProductsListAdapter(
    val data: ArrayList<MarketSellData>,
    val data1: ArrayList<ProductData>,
) :
    RecyclerView.Adapter<SelledProductsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.selled_products, parent, false
        )
    )

    private var listener: ((Int) -> Unit)? = null

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.apply {
                productDelete.setOnClickListener {
                    listener?.invoke(adapterPosition)
                }
            }
        }


        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val d = data[position]
            val d1 = data1[position]
            itemView.apply {
                productName.text = (d1.code + "-" + d1.name)

                productPrice.text =
                    "Нарх: ${d.quantity} ${d1.unit} x ${d.agent_sell_price} = ${d.quantity * d.agent_sell_price}"

                if (d.discount != 0.0) {
                    discountPrice.visibility = View.VISIBLE
                    discountPrice.text =
                        "Чэгирма:: ${d.quantity * d.agent_sell_price} x ${d.discount / 100.0} = ${d.quantity * d.agent_sell_price * d.discount / 100.0}"
                }


            }
        }
    }

    fun setOnDeleteChosen(f: ((Int) -> Unit)?) {
        listener = f
    }

}
