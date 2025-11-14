package com.example.myfragments.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfragments.R
import com.example.myfragments.domain.modelo.Scp

class ScpItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
    private val textViewName: TextView = itemView.findViewById(R.id.textViewName)

    fun bind(scp: Scp, click: (Scp) -> Unit) {
        val formatted = String.format("SCP %03d", scp.item)
        textViewItem.text = formatted
        textViewName.text = scp.nombre
        itemView.setOnClickListener { click(scp) }
    }
}
