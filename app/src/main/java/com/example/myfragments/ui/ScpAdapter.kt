package com.example.myfragments.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myfragments.R

import com.example.myfragments.domain.modelo.Scp

class ScpAdapter(
    private val itemClick: (Scp) -> Unit
) : ListAdapter<Scp, ScpItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScpItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_scp, parent, false)
        return ScpItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScpItemViewHolder, position: Int) {
        holder.bind(getItem(position), itemClick)
    }

    class DiffCallback : DiffUtil.ItemCallback<Scp>() {
        override fun areItemsTheSame(oldItem: Scp, newItem: Scp): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Scp, newItem: Scp): Boolean = oldItem == newItem
    }
}
