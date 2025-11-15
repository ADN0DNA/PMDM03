package com.example.myfragments.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfragments.databinding.ItemSiteSelectorBinding
import com.example.myfragments.domain.modelo.Site

class SiteSelectorAdapter(
    private val onSiteToggled: (Site, Boolean) -> Unit
) : ListAdapter<SiteSelectorItem, SiteSelectorAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSiteSelectorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onSiteToggled)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemSiteSelectorBinding,
        private val onSiteToggled: (Site, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SiteSelectorItem) {
            binding.checkBoxSite.text = "Site-${item.site.code} (${item.site.location})"
            binding.checkBoxSite.isChecked = item.isSelected

            binding.checkBoxSite.setOnCheckedChangeListener { _, isChecked ->
                onSiteToggled(item.site, isChecked)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SiteSelectorItem>() {
        override fun areItemsTheSame(oldItem: SiteSelectorItem, newItem: SiteSelectorItem) =
            oldItem.site.id == newItem.site.id

        override fun areContentsTheSame(oldItem: SiteSelectorItem, newItem: SiteSelectorItem) =
            oldItem == newItem
    }
}

data class SiteSelectorItem(
    val site: Site,
    val isSelected: Boolean
)

