package com.mobile.liderstoreagent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.ClientProducts
import com.mobile.liderstoreagent.utils.spannableText
import kotlinx.android.synthetic.main.item_client_products_for_view.view.*


class ClientProductListAdapterForView : ListAdapter<ClientProducts, ClientProductListAdapterForView.ViewHolder>(
    DiffItem
) {
    var query = ""
    object DiffItem : DiffUtil.ItemCallback<ClientProducts>() {
        override fun areItemsTheSame(
                oldItem: ClientProducts,
                newItem: ClientProducts,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
                oldItem: ClientProducts,
                newItem: ClientProducts,
        ): Boolean {
            return oldItem.created_date == newItem.created_date &&
                    oldItem.price == newItem.price &&
                    oldItem.product == newItem.product &&
                    oldItem.quantity == newItem.quantity
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_client_products_for_view, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(d: ClientProducts) {
            itemView.apply {

                var dw = d.product.code + " " + d.product.name.toUpperCase()
                dw = dw.toUpperCase()
                if (query != "") soldItemNameClient.text = dw spannableText query
                else soldItemNameClient.text  = dw


                if (d.status.equals("ordered")) {
                    soldItemStatusClient.text = "Буюртма қилинди"
                    soldItemStatusClient.setTextColor(resources.getColor(R.color.error))
                } else if (d.status.equals("delivered")) {
                    soldItemStatusClient.text = "Етказилди"
                    soldItemStatusClient.setTextColor(resources.getColor(R.color.success))
                }
                soldItemQuantityClient.text = d.quantity + " " + d.product.unit
                soldItemPriceClient.text = "${d.price.price} $"
                soldItemDateClient.text = d.created_date.substring(0, 10)

            }
        }
    }

}