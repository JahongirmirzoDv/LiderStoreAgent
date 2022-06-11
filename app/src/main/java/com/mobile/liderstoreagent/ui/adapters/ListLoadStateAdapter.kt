package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.databinding.ViewHolderLoadStateBinding

/**
 *Created by farrukh_kh on 6/21/21 10:57 AM
 *uz.algorithmgateway.uymarket.utils.adapters.recycler
 **/
class ListLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ListLoadStateAdapter.ListLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ListLoadStateViewHolder(
            ViewHolderLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ListLoadStateViewHolder, loadState: LoadState) {
        holder.onBindLoadState(loadState)
    }

    inner class ListLoadStateViewHolder(private val binding: ViewHolderLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun onBindLoadState(loadState: LoadState) = with(binding) {
            pbLoading.isVisible = loadState is LoadState.Loading
            txvError.isVisible = loadState !is LoadState.Loading
            btnRetry.isVisible = loadState !is LoadState.Loading
            if (loadState is LoadState.Error) {
                txvError.text = loadState.error.message
            }
        }
    }
}