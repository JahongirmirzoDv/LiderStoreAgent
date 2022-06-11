package com.mobile.liderstoreagent.ui.dialogs
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.discountsmodel.Discounts
import com.mobile.liderstoreagent.ui.adapters.DiscountsListAdapter
import kotlinx.android.synthetic.main.discounts_dialog.view.*


class DiscountChooseDialog(context: Context, discounts: List<Discounts>) :
    BaseDialog(context, R.layout.discounts_dialog) {
    private var listener: ((Int,String,String,String) -> Unit)? = null
    private var adapter = DiscountsListAdapter(discounts)

    init {
        view.apply {
            recyclerDiscounts.adapter = adapter
            recyclerDiscounts.layoutManager = LinearLayoutManager(context)
            var date=""
            adapter.setOnDiscountChosen { id,name,discount,deadline ->

                if(deadline.isNullOrBlank()){
                    date = "Nomalum"
                }
                else {
                    listener?.invoke(id,name,discount,deadline)
                }

                close()
            }
        }
    }


    fun setDiscountChosen(f: ((Int,String,String,String) -> Unit)?) {
        listener = f
    }
}

