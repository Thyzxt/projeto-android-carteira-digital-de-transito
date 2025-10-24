package com.example.myapplication.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InfracaoDao {
    @Query("SELECT * FROM infracoes")
    fun getAll(): Flow<List<InfracaoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(infracao: InfracaoEntity)

    @Delete
    suspend fun delete(infracao: InfracaoEntity)

    @Update
    suspend fun update(infracao: InfracaoEntity)
}

@Dao
interface VeiculoDao{

    @Query("SELECT * FROM veiculos")
    fun getAll(): Flow<List<VeiculoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(veiculo: VeiculoEntity)

    @Update
    suspend fun update(veiculo: VeiculoEntity)

    @Delete
    suspend fun delete(veiculo: VeiculoEntity)
}