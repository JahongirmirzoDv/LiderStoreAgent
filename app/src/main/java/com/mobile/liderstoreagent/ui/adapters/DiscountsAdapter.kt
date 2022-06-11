package com.mobile.liderstoreagent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.getproduct.Discount
import kotlinx.android.synthetic.main.item_dialog_discounts.view.*

class DiscountsAdapter(val data: List<Discount>) :
    RecyclerView.Adapter<DiscountsAdapter.ViewHolder>() {
    private var listener : ((Int) -> Unit)?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_dialog_discounts, parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.apply {
                discount_view.setOnClickListener {
                    listener?.invoke(adapterPosition)
                }
            }
        }


        @SuppressLint("SetTextI18n")
        fun bind() {
            val d = data[adapterPosition]
            itemView.apply {
                isDiscountChosen.isEnabled = false
                isDiscountChosen.isChecked = d.isChecked
                discountText.text = d.name
                discountAmount.text = d.discount.toString() + "%"
            }
        }
    }

    fun setOnDiscountChosen(f: ((Int) -> Unit)?) {
        listener = f
    }

}
