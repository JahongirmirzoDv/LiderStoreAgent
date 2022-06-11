package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.data.models.SellToClientOwnProduct
import com.mobile.liderstoreagent.databinding.ItemOwnSellProductBinding


class ToSellClientAdapter(var list: List<SellToClientOwnProduct>): RecyclerView.Adapter<ToSellClientAdapter.VH>() {

    inner class VH(val binding: ItemOwnSellProductBinding): RecyclerView.ViewHolder(binding.root)

    private var clik_delete: ((Int) -> Unit)? = null

    private var clik_edit: ((SellToClientOwnProduct, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemOwnSellProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            val item = list[position]

            productName.text = item.name
            quantity.text = "miqdori: ${item.quantity}"
            price.text = "narxi: ${item.price}"

            cardDelete.setOnClickListener {
                clik_delete?.invoke(position)
            }

            cardEdit.setOnClickListener {
                clik_edit?.invoke(item, position)
            }
        }
    }

    fun clickedD(d: ((Int) -> Unit)) {
        clik_delete = d
    }

    fun clickedE(d: ((SellToClientOwnProduct, Int) -> Unit)) {
        clik_edit = d
    }


    override fun getItemCount(): Int = list.size
}