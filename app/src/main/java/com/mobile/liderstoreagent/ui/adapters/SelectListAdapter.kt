package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import kotlinx.android.synthetic.main.discounts_item.view.*

class SelectListAdapter(val data: List<String>) :
    RecyclerView.Adapter<SelectListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.discounts_item, parent, false
        )
    )

    private var listener: ((String) -> Unit)? = null

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.apply {
                discount_name.setOnClickListener {
                    val d = data[adapterPosition]
                    if (d.isNotEmpty())
                        listener?.invoke(d)
                }
            }
        }


        fun bind() {
            val d = data[adapterPosition]
            itemView.apply {
                discount_name.text = d
            }
        }
    }

    fun setOnDiscountChosen(f: ((String) -> Unit)?) {
        listener = f
    }

}
