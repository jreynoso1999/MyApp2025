package com.example.myapp2025.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personas")
data class Person(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "identificacion") val identificacion: String,
    @ColumnInfo(name = "nombres") val nombres: String,
    @ColumnInfo(name = "apellidos") val apellidos: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "telefono") val telefono: String,
    @ColumnInfo(name = "direccion") val direccion: String,
    @ColumnInfo(name = "fechanacimiento") val fechanacimiento: String,
    @ColumnInfo(name = "avatar") val avatar: String? = null // opcional
)
