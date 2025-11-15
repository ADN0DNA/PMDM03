package com.example.myfragments.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myfragments.R
import com.example.myfragments.domain.modelo.Site

class SiteAdapter(
    private val itemClick: (Site) -> Unit
) : ListAdapter<Site, SiteItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_site, parent, false)
        return SiteItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SiteItemViewHolder, position: Int) {
        holder.bind(getItem(position), itemClick)
    }

    class DiffCallback : DiffUtil.ItemCallback<Site>() {
        override fun areItemsTheSame(oldItem: Site, newItem: Site): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Site, newItem: Site): Boolean = oldItem == newItem
    }
}

