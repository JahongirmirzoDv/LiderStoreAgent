package com.mobile.liderstoreagent.ui.dialogs

import android.content.Context
import com.mobile.liderstoreagent.R
import kotlinx.android.synthetic.main.expense_choose_dialog.view.*


class ExpenseChooseDialog(context: Context) :
    BaseDialog(context, R.layout.expense_choose_dialog) {
    private var listener: ((Int) -> Unit)? = null


    init {
        view.apply {

            expenseAll.setOnClickListener {
                listener?.invoke(1)
                close()
            }

            expenseApproved.setOnClickListener {
                listener?.invoke(2)
                close()
            }

            expenseOnlyPersonal.setOnClickListener {
                listener?.invoke(3)
                close()
            }

            expenseOnlyFirm.setOnClickListener {
                listener?.invoke(4)
                close()
            }

        }
    }

    fun setOnExpenseChosen(f: ((Int) -> Unit)?) {
        listener = f
    }
}

