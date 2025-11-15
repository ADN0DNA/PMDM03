package com.example.myfragments.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfragments.R
import com.example.myfragments.data.local.entity.ScpWithSites

class ScpItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
    private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
    private val textViewSite: TextView = itemView.findViewById(R.id.textViewSite)

    fun bind(scpWithSites: ScpWithSites, click: (Int) -> Unit) {
        val scp = scpWithSites.scp
        val formatted = String.format("SCP %03d", scp.item)
        textViewItem.text = formatted
        textViewName.text = scp.nombre

        // Mostrar el código del primer Site asociado, o vacío si no tiene
        if (scpWithSites.sites.isNotEmpty()) {
            textViewSite.text = scpWithSites.sites.first().code.toString()
        } else {
            textViewSite.text = "-"
        }

        itemView.setOnClickListener { click(scp.id) }
    }
}
