package com.mobile.liderstoreagent.ui.dialogs

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.warehouse.WarehouseData
import com.mobile.liderstoreagent.ui.adapters.reporthistory.WarehousesListAdapter
import kotlinx.android.synthetic.main.category_dialog.view.*


class WarehouseChooseDialog(context: Context, warehouses: List<WarehouseData.Warehouse>) :
    BaseDialog(context, R.layout.category_dialog) {
    private var listener: ((Int) -> Unit)? = null
    private var adapter = WarehousesListAdapter(warehouses)

    init {
        view.apply {
            tvTitleWarehouse.text = "Омборлар"
            recyclerCategoriesWarehouse.layoutManager = LinearLayoutManager(context)
            recyclerCategoriesWarehouse.adapter = adapter

            adapter.setOnCategoryChosen { data ->
                listener?.invoke(data)
                close()
            }


        }
    }

    fun setOnWarehouseChosen(f: ((Int) -> Unit)?) {
        listener = f
    }

}
