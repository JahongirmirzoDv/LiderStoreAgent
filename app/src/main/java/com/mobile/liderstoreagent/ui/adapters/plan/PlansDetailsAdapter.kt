package com.mobile.liderstoreagent.ui.adapters.plan

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.planmodel.PlanDetail
import kotlinx.android.synthetic.main.item_plan_details.view.*

class PlansDetailsAdapter : ListAdapter<PlanDetail, PlansDetailsAdapter.ViewHolder>(DiffItem) {

    var query = ""

    object DiffItem : DiffUtil.ItemCallback<PlanDetail>() {
        override fun areItemsTheSame(oldItem: PlanDetail, newItem: PlanDetail): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: PlanDetail, newItem: PlanDetail): Boolean {
            return oldItem.percentage == newItem.percentage &&
                    oldItem.quantity == newItem.quantity &&
                    oldItem.product.id == newItem.product.id &&
                    oldItem.product.category == newItem.product.category &&
                    oldItem.product.name == newItem.product.name &&
                    oldItem.product.product_type == newItem.product.product_type &&
                    oldItem.product.unit == newItem.product.unit &&
                    oldItem.product.provider == newItem.product.provider
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_plan_details, parent, false)
    )




    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

            }
        }

        fun bind(d: PlanDetail) {
            itemView.apply {
                planDetailItemName.text = d.product.name
                planDetailItemPercent.text = d.percentage + " %"
                planItemDetailQuantity.text = "${d.quantity} ${d.product.unit}"

                if (d.product.product_type == "limited") {
                    planDetailItemStatus.text = "Vip emas"
                    planDetailItemStatus.setTextColor(Color.RED)
                } else if (d.product.product_type == "unlimited") {
                    planDetailItemStatus.text = "Vip"
                    planDetailItemStatus.setTextColor(Color.GREEN)
                }
            }
        }
    }


}
