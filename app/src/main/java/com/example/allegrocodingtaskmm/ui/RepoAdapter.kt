package com.example.allegrocodingtaskmm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.allegrocodingtaskmm.R
import com.example.allegrocodingtaskmm.model.Repo
import com.example.allegrocodingtaskmm.ui.RepoAdapter.RepoViewHolder


class RepoAdapter(private val onEntryListener: OnEntryClickListener) :
    PagingDataAdapter<Repo, RepoViewHolder>(DiffCallback) {

    class RepoViewHolder(itemView: View, private val onEntryListener: OnEntryClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val repoNameTextView: TextView = itemView.findViewById(R.id.fullRepoNameTextView)
        private val repoDescTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(element: Repo) {
            repoNameTextView.text = element.name
            repoDescTextView.text = element.language ?: "Language not specified."
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
        return RepoViewHolder(view, onEntryListener)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        if (repo != null) {
            holder.bind(repo)
            holder.itemView.setOnClickListener { onEntryListener.onEntryClick(repo) }

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
            oldItem == newItem
    }
}


interface OnEntryClickListener {
    fun onEntryClick(clickedRepo: Repo)
}


