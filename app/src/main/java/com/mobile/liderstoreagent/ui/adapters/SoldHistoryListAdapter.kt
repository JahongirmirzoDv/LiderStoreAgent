package com.mobile.liderstoreagent.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.historymodel.SoldProductHistory
import com.mobile.liderstoreagent.utils.spannableText
import kotlinx.android.synthetic.main.item_sold_product_hisyory.view.*

class SoldHistoryListAdapter :
    ListAdapter<SoldProductHistory, SoldHistoryListAdapter.ViewHolder>(DiffItem) {

    var query = ""

    object DiffItem : DiffUtil.ItemCallback<SoldProductHistory>() {
        override fun areItemsTheSame(
            oldItem: SoldProductHistory,
            newItem: SoldProductHistory
        ): Boolean {
            return oldItem.created_date == newItem.created_date
        }

        override fun areContentsTheSame(
            oldItem: SoldProductHistory,
            newItem: SoldProductHistory
        ): Boolean {
            return oldItem.client == newItem.client &&
                    oldItem.product_name == newItem.product_name &&
                    oldItem.status == newItem.status &&
                    oldItem.price == newItem.price &&
                    oldItem.created_date == newItem.created_date &&
                    oldItem.quantity == newItem.quantity
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sold_product_hisyory, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

            }
        }

        fun bind(d: SoldProductHistory) {
            itemView.apply {
                if (query != "") soldItemName.text = d.product_name spannableText query
                else soldItemName.text = d.product_name

                soldItemClient.text = d.client
                soldItemDate.text = d.created_date.substring(0, 10) +" "+ d.created_date.substring(11, 16)
                soldItemPrice.text = d.price
                soldItemQuantity.text = d.quantity

                if (d.status.equals("ordered")) {
                    soldItemStatus.text = "Буюртма қилинди"
                    soldItemStatus.setTextColor(Color.RED)
                } else if (d.status.equals("delivered")) {
                    soldItemStatus.text = "Етказилди"
                    soldItemStatus.setTextColor(Color.GREEN)
                }

            }
        }
    }


}
