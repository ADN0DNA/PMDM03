package com.example.myfragments.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfragments.R
import com.example.myfragments.domain.modelo.Site

class SiteItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textViewSiteCode: TextView = itemView.findViewById(R.id.textViewSiteCode)
    private val textViewLocation: TextView = itemView.findViewById(R.id.textViewLocation)
    private val textViewSecurityLevel: TextView = itemView.findViewById(R.id.textViewSecurityLevel)
    private val textViewDirector: TextView = itemView.findViewById(R.id.textViewDirector)

    fun bind(site: Site, click: (Site) -> Unit) {
        textViewSiteCode.text = "Site-${site.code}"
        textViewLocation.text = site.location
        textViewSecurityLevel.text = site.securityLevel
        textViewDirector.text = "Director: ${site.director}"
        itemView.setOnClickListener { click(site) }
    }
}

