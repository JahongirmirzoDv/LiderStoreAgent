package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
import com.mobile.liderstoreagent.utils.spannableText
import kotlinx.android.synthetic.main.item_search_client.view.*

class ClientSearchListAdapter :
    ListAdapter<ClientsData, ClientSearchListAdapter.ViewHolder>(DiffItem) {

    var query = ""

    object DiffItem : DiffUtil.ItemCallback<ClientsData>() {
        override fun areItemsTheSame(oldItem: ClientsData, newItem: ClientsData): Boolean {
            return oldItem.client.id == newItem.client.id
        }

        override fun areContentsTheSame(oldItem: ClientsData, newItem: ClientsData): Boolean {
            return oldItem.client.name == newItem.client.name &&
                    oldItem.client.responsible_agent == newItem.client.responsible_agent &&
                    oldItem.client.work_phone_number == newItem.client.work_phone_number &&
                    oldItem.client.address == newItem.client.address &&
                    oldItem.total_debt == newItem.total_debt
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_search_client, parent, false)
    )

    private var listenerClientData: ((Int, String, Double) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {
                clientSearchableName.setOnClickListener {
                    listenerClientData?.invoke(
                        getItem(adapterPosition).client.id,
                        getItem(adapterPosition).client.name,
                        getItem(adapterPosition).total_debt
                    )
                }
            }
        }
//        if (query != "") productName.text = d.name spannableText query
//        else productName.text = d.name

        fun bind(d: ClientsData) {
            itemView.apply {
                if (query != "") clientSearchableName.text = d.client.name spannableText query
                else clientSearchableName.text = d.client.name
            }
        }
    }


    fun setOnClientChosenListener(f: (Int, String, Double) -> Unit) {
        listenerClientData = f
    }

}
