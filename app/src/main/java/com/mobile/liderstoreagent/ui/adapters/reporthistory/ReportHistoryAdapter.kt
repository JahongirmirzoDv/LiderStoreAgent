package com.mobile.liderstoreagent.ui.adapters.reporthistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.reportmodel.ReportHistory
import kotlinx.android.synthetic.main.item_report_hisyory.view.*

class ReportHistoryAdapter :
    ListAdapter<ReportHistory, ReportHistoryAdapter.ViewHolder>(DiffItem) {

    var query = ""

    object DiffItem : DiffUtil.ItemCallback<ReportHistory>() {
        override fun areItemsTheSame(
            oldItem: ReportHistory,
            newItem: ReportHistory
        ): Boolean {
            return oldItem.comment == newItem.comment
        }

        override fun areContentsTheSame(
            oldItem: ReportHistory,
            newItem: ReportHistory
        ): Boolean {
            return oldItem.comment == newItem.comment &&
                    oldItem.client == newItem.client &&
                    oldItem.agent == oldItem.agent
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report_hisyory, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

            }
        }

        fun bind(d: ReportHistory) {
            itemView.apply {
                commentText.text = d.comment
                clientName.text = d.client.name
                reportDate.text = d.created_date.substring(0, 10) + " " + d.created_date.substring(11, 16)

                if (d.images.isNotEmpty()) {
                    Glide.with(reportImage.context).load("http://159.65.233.187:8000" + d.images[0])
                        .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                        .into(reportImage)
                }
            }
        }
    }


}
