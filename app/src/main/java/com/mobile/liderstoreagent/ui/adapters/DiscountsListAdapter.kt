package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.discountsmodel.Discounts
import kotlinx.android.synthetic.main.discounts_item.view.*

class DiscountsListAdapter (val data: List<Discounts>) :
    RecyclerView.Adapter<DiscountsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.discounts_item, parent, false
        )
    )

    private var listener: ((Int,String,String,String) -> Unit)? = null

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.apply {
                discount_name.setOnClickListener {
                    val d = data[adapterPosition]
                    if (d.deadline!=null)
                    listener?.invoke(d.id,d.name,d.discount.toString() + " %",d.deadline.toString().substring(0,10))
                    else  listener?.invoke(d.id,d.name,d.discount.toString() + " %","Noma'lum")
                }
            }
        }


        fun bind() {
            val d = data[adapterPosition]
            itemView.apply {
                discount_name.text = d.name
            }
        }
    }

    fun setOnDiscountChosen(f: ((Int,String,String,String) -> Unit)?) {
        listener = f
    }

}
