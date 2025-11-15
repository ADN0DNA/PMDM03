package com.example.myfragments.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SiteWithScps(
    @Embedded val site: SiteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = SiteScpCrossRef::class,
            parentColumn = "siteId",
            entityColumn = "scpId"
        )
    )
    val scps: List<ScpEntity>
)

