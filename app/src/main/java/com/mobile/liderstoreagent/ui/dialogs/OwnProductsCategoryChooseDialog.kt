package com.mobile.liderstoreagent.ui.dialogs

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.warehouse.AgentData
import com.mobile.liderstoreagent.ui.adapters.OwnCategoryListAdapter
import kotlinx.android.synthetic.main.category_dialog.view.*


class OwnProductsCategoryChooseDialog(context: Context, categories: List<AgentData.CategoryModel>) :
    BaseDialog(context, R.layout.category_dialog) {
    private var listener: ((Int) -> Unit)? = null
    private var adapter = OwnCategoryListAdapter(categories)


    init {
        view.apply {
            tvTitleWarehouse.text = "Махсулот категориялари"
            recyclerCategoriesWarehouse.adapter = adapter
            recyclerCategoriesWarehouse.layoutManager = LinearLayoutManager(context)

            adapter.setOnCategoryChosen { data ->
                listener?.invoke(data)
                close()
            }
        }

    }

    fun setOnCategoryChosen(f: ((Int) -> Unit)?) {
        listener = f
    }
}

