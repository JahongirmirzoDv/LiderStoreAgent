package com.mobile.liderstoreagent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.utils.spannableText
import kotlinx.android.synthetic.main.item_client.view.*
import kotlin.math.abs

class ClientListAdapter :
    ListAdapter<com.mobile.liderstoreagent.data.models.clientmodel.ClientsData, ClientListAdapter.ViewHolder>(
        DiffItem
    ) {

    var query = ""

    private var listenerClientData: ((Int) -> Unit)? = null
    private var listener: ((Int) -> Unit)? = null
    private var clientClicks: ((Int, Double) -> Unit)? = null

    object DiffItem :
        DiffUtil.ItemCallback<com.mobile.liderstoreagent.data.models.clientmodel.ClientsData>() {
        override fun areItemsTheSame(
            oldItem: com.mobile.liderstoreagent.data.models.clientmodel.ClientsData,
            newItem: com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
        ): Boolean {
            return oldItem.client.id == newItem.client.id
        }

        override fun areContentsTheSame(
            oldItem: com.mobile.liderstoreagent.data.models.clientmodel.ClientsData,
            newItem: com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
        ): Boolean {
            return oldItem.client.name == newItem.client.name &&
                    oldItem.client.responsible_agent == newItem.client.responsible_agent &&
                    oldItem.client.work_phone_number == newItem.client.work_phone_number &&
                    oldItem.client.address == newItem.client.address &&
                    oldItem.total_debt == newItem.total_debt
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(d: com.mobile.liderstoreagent.data.models.clientmodel.ClientsData) {

            itemView.apply {
                var name = d.client.market_code.toString() + "-" + d.client.name
                name = name.toUpperCase()

                if (query != "") client_name.text = name spannableText query
                else client_name.text = name

                client_address.text = "Манзил: ${d.client.address}"
                responsible_agent.text = "Масъул: ${d.client.responsible_agent}"
                phone.text = "Тел: ${d.client.work_phone_number}"


                if (d.total_debt > 0) {
                    debt.setTextColor(resources.getColor(R.color.error))
                    debt.text = "Қарз: ${abs(d.total_debt)} $"
                } else {
                    debt.setTextColor(resources.getColor(R.color.success))
                    debt.text = "Плус: ${abs(d.total_debt)} $"
                }


                editClient.setOnClickListener {
                    listener?.invoke(d.client.id)
                }

                clientClick.setOnClickListener {
                    clientClicks?.invoke(d.client.id, d.total_debt)
                }

                printClientData.setOnClickListener {
                    listenerClientData?.invoke(d.client.id)
                }

            }
        }
    }


    fun setOnClientChosen(f: ((Int) -> Unit)?) {
        listener = f
    }


    fun setOnClientClick(f: ((Int, Double) -> Unit)?) {
        clientClicks = f
    }


    fun printClientDataClick(f: ((Int) -> Unit)?) {
        listenerClientData = f
    }


}