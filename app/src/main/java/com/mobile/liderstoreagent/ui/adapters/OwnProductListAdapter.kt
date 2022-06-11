package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.data.models.warehouse_product.Result
import com.mobile.liderstoreagent.databinding.ItemOwnProductsBinding
import com.mobile.liderstoreagent.utils.spannableText

class OwnProductListAdapter : PagingDataAdapter<Result, OwnProductListAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<Result>() {

    override fun areItemsTheSame(oldItem: Result, newItem: Result) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Result, newItem: Result) =
        oldItem == newItem

    override fun getChangePayload(oldItem: Result, newItem: Result) = false
}) {
    var query = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOwnProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class ViewHolder(val binding: ItemOwnProductsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val result = getItem(position)
            cardSmall.visibility = View.GONE
            if (result != null) {
                if (query != "") productName.text = result.product.name spannableText query
                else productName.text = result.product.name
                productDate.text = result.created_date.substring(0, 10)
                productQuantity.text = result.quantity
                productPrice.text = result.incoming_price
            }

        }
    }

}