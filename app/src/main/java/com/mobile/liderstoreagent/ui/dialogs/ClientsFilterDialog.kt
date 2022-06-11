package com.mobile.liderstoreagent.ui.dialogs

import android.content.Context
import com.mobile.liderstoreagent.R
import kotlinx.android.synthetic.main.clients_dialog.view.*


class ClientsFilterDialog(context: Context) :
    BaseDialog(context, R.layout.clients_dialog) {
    private var listener: ((String) -> Unit)? = null
    init {
        view.apply {

            allClients.setOnClickListener {
                listener?.invoke("all")
                close()
            }

            noDebtClients.setOnClickListener {
                listener?.invoke("no_debt")
                close()
            }


            debtClients.setOnClickListener {
                listener?.invoke("debt")
                close()
            }

        }
    }

    fun setOnClientFilterChosen(f: ((String) -> Unit)?) {
        listener = f
    }
}

