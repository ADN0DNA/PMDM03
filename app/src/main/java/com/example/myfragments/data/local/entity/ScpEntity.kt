package com.example.myfragments.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfragments.domain.modelo.Clase
import com.example.myfragments.domain.modelo.Classification

@Entity(
tableName = "scp",
)
data class ScpEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val item: Int = 0,
    val nombre: String = "",
    val clase: Clase = Clase.SAFE,
    val favorite: Boolean = false,
    val classification: Classification = Classification.UNRESTRICTED,
    val description: String = "",
)

