package com.example.myfragments.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myfragments.R
import com.example.myfragments.data.local.entity.ScpWithSites

class ScpAdapter(
    private val itemClick: (Int) -> Unit
) : ListAdapter<ScpWithSites, ScpItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScpItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_scp, parent, false)
        return ScpItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScpItemViewHolder, position: Int) {
        holder.bind(getItem(position), itemClick)
    }

    class DiffCallback : DiffUtil.ItemCallback<ScpWithSites>() {
        override fun areItemsTheSame(oldItem: ScpWithSites, newItem: ScpWithSites): Boolean =
            oldItem.scp.id == newItem.scp.id

        override fun areContentsTheSame(oldItem: ScpWithSites, newItem: ScpWithSites): Boolean =
            oldItem == newItem
    }
}
