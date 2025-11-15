package com.example.myfragments.data.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["siteId", "scpId"])
data class SiteScpCrossRef(
    val siteId: Int,
    val scpId: Int
)

