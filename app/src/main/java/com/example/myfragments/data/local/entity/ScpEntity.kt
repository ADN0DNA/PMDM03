package com.example.myfragments.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfragments.domain.modelo.Clase
import com.example.myfragments.domain.modelo.Classification
import com.example.myfragments.domain.modelo.Scp

@Entity(
tableName = "scps"
)
data class ScpEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val item: Int,
    val nombre: String,
    val clase: Clase = Clase.SAFE,
    val favorite: Boolean = false,
    val classification: Classification = Classification.UNRESTRICTED,
    val description: String,
)

fun ScpEntity.toScp()= Scp(
        id = this.id,
        item = this.item,
        nombre = this.nombre,
        clase = this.clase,
        favorite = this.favorite,
        classification = this.classification,
        description = this.description,
    )

fun Scp.toScpEntity()= ScpEntity(
        id = this.id,
        item = this.item,
        nombre = this.nombre,
        clase = this.clase,
        favorite = this.favorite,
        classification = this.classification,
        description = this.description,
    )