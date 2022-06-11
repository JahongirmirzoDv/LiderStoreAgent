package com.mobile.liderstoreagent.ui.dialogs

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.ui.adapters.SelectListAdapter
import kotlinx.android.synthetic.main.discounts_dialog.view.*

class SelectorDialog(context: Context, discounts: List<String>) :
    BaseDialog(context, R.layout.selector_dialog) {
    private var listener: ((String) -> Unit)? = null
    private var adapter = SelectListAdapter(discounts)

    init {
        view.apply {
            recyclerDiscounts.adapter = adapter
            recyclerDiscounts.layoutManager = LinearLayoutManager(context)

            adapter.setOnDiscountChosen { name ->

                listener?.invoke(name)


                close()
            }
        }
    }


    fun setSelectedName(f: ((String) -> Unit)?) {
        listener = f
    }
}

