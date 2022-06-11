package com.mobile.liderstoreagent.ui.adapters.plan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.planmodel.PlanData
import kotlinx.android.synthetic.main.item_plans.view.*

class PlansAdapter : ListAdapter<PlanData, PlansAdapter.ViewHolder>(DiffItem) {

    var query = ""

    object DiffItem : DiffUtil.ItemCallback<PlanData>() {
        override fun areItemsTheSame(oldItem: PlanData, newItem: PlanData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlanData, newItem: PlanData): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.deadline == newItem.deadline &&
                    oldItem.percent == newItem.percent
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_plans, parent, false)
    )

    private var listener: ((Int) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {
                planDetail.setOnClickListener {
                    listener?.invoke(getItem(adapterPosition).id.toInt())
                }
            }
        }

        fun bind(d: PlanData) {
            itemView.apply {
                planItemName.text = d.name
                planItemDeadline.text = d.deadline

                val pie = AnyChart.pie()
                val arrayTitle = arrayOf<String>("Bajarildi", "Bajarilmadi")
                val arrayValue = arrayOf<Float>(d.percent.toFloat(), 100f - d.percent.toFloat())
                var list = ArrayList<DataEntry>()
                list.add(ValueDataEntry(arrayTitle[0], arrayValue[0]))
                list.add(ValueDataEntry(arrayTitle[1], arrayValue[1]))
                pie.data(list)
                anyChartView.setChart(pie)//--> XML id = anyChartView

            }
        }
    }

    fun setOnPlanClicked(f: ((Int) -> Unit)?) {
        listener = f
    }


}
