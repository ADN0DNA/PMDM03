package com.example.myfragments.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ScpWithSites(
    @Embedded val scp: ScpEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = SiteScpCrossRef::class,
            parentColumn = "scpId",
            entityColumn = "siteId"
        )
    )
    val sites: List<SiteEntity>
)

