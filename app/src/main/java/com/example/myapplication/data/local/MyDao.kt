package com.example.myapplication.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {

    @Query("SELECT * FROM infracoes")
    fun getAllInfracoes(): Flow<List<InfracaoEntity>>

    @Query("SELECT * FROM infracoes WHERE id = :id")
    suspend fun getInfracaoById(id: Int): InfracaoEntity?

    @Query("SELECT * FROM infracoes WHERE status = :status")
    fun getInfracoesByStatus(status: Status): Flow<List<InfracaoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfracao(infracao: InfracaoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllInfracoes(infracoes: List<InfracaoEntity>)

    @Update
    suspend fun updateInfracao(infracao: InfracaoEntity)

    @Delete
    suspend fun deleteInfracao(infracao: InfracaoEntity)

    @Query("DELETE FROM infracoes WHERE id = :id")
    suspend fun deleteInfracaoById(id: Int)

    @Query("DELETE FROM infracoes")
    suspend fun deleteAllInfracoes()


    @Query("SELECT * FROM veiculos")
    fun getAllVeiculos(): Flow<List<VeiculoEntity>>

    @Query("SELECT * FROM veiculos WHERE id = :id")
    suspend fun getVeiculoById(id: Int): VeiculoEntity?

    @Query("SELECT * FROM veiculos WHERE placa = :placa")
    suspend fun getVeiculoByPlaca(placa: String): VeiculoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVeiculo(veiculo: VeiculoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllVeiculos(veiculos: List<VeiculoEntity>)

    @Update
    suspend fun updateVeiculo(veiculo: VeiculoEntity)

    @Delete
    suspend fun deleteVeiculo(veiculo: VeiculoEntity)

    @Query("DELETE FROM veiculos WHERE id = :id")
    suspend fun deleteVeiculoById(id: Int)

    @Query("DELETE FROM veiculos")
    suspend fun deleteAllVeiculos()
}