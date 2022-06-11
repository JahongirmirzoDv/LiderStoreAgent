package com.mobile.liderstoreagent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.source.local.clients.ClientEntity
import kotlinx.android.synthetic.main.saved_clients_item.view.*

class SavedClientsAdapter(val data: List<ClientEntity>) :
    RecyclerView.Adapter<SavedClientsAdapter.ViewHolder>() {
    private var listenerDelete: ((Int) -> Unit)? = null
    private var listenerAdd: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.saved_clients_item, parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.apply {
                addSavedClient.setOnClickListener {
                    listenerAdd?.invoke(adapterPosition)
                }

                deleteSavedClient.setOnClickListener {
                    listenerDelete?.invoke(adapterPosition)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            val d = data[adapterPosition]
            itemView.apply {
                clientInfo.text = "Номи: ${d.marketName}\nМанзили: ${d.address}\n" +
                        "Директор исми: ${d.directorName}\nДиректор тел: ${d.directorPhone}\nТуғилган куни: ${d.birthDate}\n" +
                        "Маъсул: ${d.responsiblePerson}\nИшхона тел: ${d.workPhone}\n" +
                        "Ҳудуд: ${d.target}\n"
            }
        }
    }

    fun setOnDelete(f: ((Int) -> Unit)?) {
        listenerDelete = f
    }


    fun setOnAdd(f: ((Int) -> Unit)?) {
        listenerAdd = f
    }

}
