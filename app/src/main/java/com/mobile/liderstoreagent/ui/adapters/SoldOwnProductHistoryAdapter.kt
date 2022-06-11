package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.databinding.ItemOwnProductsBinding
import com.mobile.liderstoreagent.utils.spannableText

class SoldOwnProductHistoryAdapter() : PagingDataAdapter<com.mobile.liderstoreagent.data.models.soldownproductlist.Result, SoldOwnProductHistoryAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<com.mobile.liderstoreagent.data.models.soldownproductlist.Result>() {

    override fun areItemsTheSame(oldItem: com.mobile.liderstoreagent.data.models.soldownproductlist.Result, newItem: com.mobile.liderstoreagent.data.models.soldownproductlist.Result) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: com.mobile.liderstoreagent.data.models.soldownproductlist.Result, newItem: com.mobile.liderstoreagent.data.models.soldownproductlist.Result) =
        oldItem == newItem

    override fun getChangePayload(oldItem: com.mobile.liderstoreagent.data.models.soldownproductlist.Result, newItem: com.mobile.liderstoreagent.data.models.soldownproductlist.Result) = false
}) {
    var query = ""

    private var clickReturn: ((com.mobile.liderstoreagent.data.models.soldownproductlist.Result) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOwnProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val result = getItem(position)

            if (result!= null) {
                if (query != "") productName.text = result.product spannableText query
                else productName.text = result.product
                productDate.text = result.created_date.substring(0, 10)
                productQuantity.text = "${result.quantity}"
                productPrice.text = result.price

                cardSmall.setOnClickListener {
                    clickReturn?.invoke(result)
                }
            }


        }
    }

    fun returnClickListener(c: (com.mobile.liderstoreagent.data.models.soldownproductlist.Result) -> Unit) {
        clickReturn = c
    }

    inner class ViewHolder(val binding: ItemOwnProductsBinding) : RecyclerView.ViewHolder(binding.root)
}