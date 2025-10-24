package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

enum class Status {
    VENCIDA,
    A_VENCER,
    PAGA
}

@Entity(tableName = "infracoes")
data class InfracaoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val descricao: String,
    val data: String,
    val valor: Double,
    val status: Status
)

class Converters {
    @TypeConverter
    fun fromStatus(status: Status): String = status.name

    @TypeConverter
    fun toStatus(value: String): Status = Status.valueOf(value)
}

@Entity(tableName = "veiculos")
data class VeiculoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val modelo: String,
    val placa: String,
    val descricao: String,
    val statusColor: Long
)