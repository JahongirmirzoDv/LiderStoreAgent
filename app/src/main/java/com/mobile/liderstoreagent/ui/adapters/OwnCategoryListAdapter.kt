package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.warehouse.AgentData
import kotlinx.android.synthetic.main.category_item.view.*

class OwnCategoryListAdapter(val data: List<AgentData.CategoryModel>) :
    RecyclerView.Adapter<OwnCategoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.category_item, parent, false
        )
    )

    private var listener: ((Int) -> Unit)? = null

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.apply {
                categoryName.setOnClickListener {
                    listener?.invoke(data[adapterPosition].category_id)
                }
            }
        }

        fun bind() {
            val d = data[adapterPosition]
            itemView.apply {
                categoryName.text = d.category
            }
        }
    }

    fun setOnCategoryChosen(f: ((Int) -> Unit)?) {
        listener = f
    }

}