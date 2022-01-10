package com.example.allegrocodingtaskmm.ui

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ReposLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ReposLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ReposLoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)
}