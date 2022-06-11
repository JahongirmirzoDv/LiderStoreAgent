package com.mobile.liderstoreagent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import kotlinx.android.synthetic.main.discounted_item_product.view.*

class DiscountedProductListAdapter :
        ListAdapter<DiscountedProduct, DiscountedProductListAdapter.ViewHolder>(DiffItem) {

    object DiffItem : DiffUtil.ItemCallback<DiscountedProduct>() {
        override fun areItemsTheSame(
                oldItem: DiscountedProduct,
                newItem: DiscountedProduct,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
                oldItem: DiscountedProduct,
                newItem: DiscountedProduct,
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.quantity == newItem.quantity &&
                    oldItem.unit == newItem.unit &&
                    oldItem.provider == newItem.provider
        }

    }

    private var clickListener: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.discounted_item_product, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {
                productSellDiscount.setOnClickListener {
                    clickListener?.invoke(getItem(adapterPosition).id)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(d: DiscountedProduct) {
            itemView.apply {
                discountedProductName.text = d.name
                discountedProductQuantity.text = d.quantity + " " + d.unit
                discountedProductProvider.text = d.provider
            }
        }
    }

    fun clickedProduct(f : (Int )->Unit) {
        clickListener = f
    }

}