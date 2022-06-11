package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.expense.AgentExpenseData
import com.mobile.liderstoreagent.utils.spannableText
import kotlinx.android.synthetic.main.item_expense.view.*

class ExpenseListAdapter : ListAdapter<AgentExpenseData, ExpenseListAdapter.ViewHolder>(DiffItem) {

    var query = ""



    object DiffItem : DiffUtil.ItemCallback<AgentExpenseData>() {
        override fun areItemsTheSame(
            oldItem: AgentExpenseData,
            newItem: AgentExpenseData
        ): Boolean {
            return oldItem.created_date == newItem.created_date
        }

        override fun areContentsTheSame(
            oldItem: AgentExpenseData,
            newItem: AgentExpenseData
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.approved == newItem.approved &&
                    oldItem.created_date == newItem.created_date &&
                    oldItem.category == newItem.category &&
                    oldItem.description == newItem.description &&
                    oldItem.updated_date == newItem.updated_date

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

            }
        }

        fun bind(d: AgentExpenseData) {

            itemView.apply {
                var name = d.name
                name = name.toUpperCase()

                if (query != "") expenseName.text = name spannableText query
                else expenseName.text = name

                expenseDescription.text = d.description
                expenseDate.text =
                    d.created_date.substring(0, 10) + " " + d.created_date.substring(11, 16)
                expenseAmount.text = d.quantity

                if (d.category == "self") ExpensesType.text = "Шахсий" else
                    ExpensesType.text = "Фирма"

                if (d.approved) {
                    expenseIsApproved.text = "Тасдиқланган"
                } else {
                    expenseIsApproved.text = "Тасдиқланмаган"
                }

            }
        }
    }

}