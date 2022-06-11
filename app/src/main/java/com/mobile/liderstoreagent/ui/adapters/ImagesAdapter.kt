package com.mobile.liderstoreagent.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import kotlinx.android.synthetic.main.item_image.view.*
import java.io.File

class ImagesAdapter : ListAdapter<File, ImagesAdapter.ViewHolder>(DiffItem) {


    object DiffItem : DiffUtil.ItemCallback<File>() {
        override fun areItemsTheSame(
            oldItem: File,
            newItem: File
        ): Boolean {
            return oldItem.path == newItem.path
        }

        override fun areContentsTheSame(
            oldItem: File,
            newItem: File
        ): Boolean {
            return oldItem.name == newItem.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

            }
        }

        fun bind(d: File) {
            itemView.apply {
               imageReport.setImageURI(Uri.fromFile(d))
            }
        }
    }

}