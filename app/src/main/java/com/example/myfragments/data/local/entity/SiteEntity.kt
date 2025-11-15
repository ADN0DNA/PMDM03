package com.example.myfragments.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfragments.domain.modelo.Site

@Entity(
tableName = "sites"
)

data class SiteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val code: Int,
    val location: String,
    val securityLevel: String,
    val director: String,
)

fun SiteEntity.toSite()= Site(
        id = this.id,
        code = this.code,
        location = this.location,
        securityLevel = this.securityLevel,
        director = this.director,
)
fun Site.toSiteEntity()= SiteEntity(
        id = this.id,
        code = this.code,
        location = this.location,
        securityLevel = this.securityLevel,
        director = this.director,
)