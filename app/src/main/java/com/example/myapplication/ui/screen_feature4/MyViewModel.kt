package com.example.myapplication.ui.screen_feature4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.VeiculoEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VeiculoViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).veiculoDao()

    val veiculos = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addVeiculo(veiculo: VeiculoEntity) = viewModelScope.launch { dao.insert(veiculo) }
    fun updateVeiculo(veiculo: VeiculoEntity) = viewModelScope.launch { dao.update(veiculo) }
    fun deleteVeiculo(veiculo: VeiculoEntity) = viewModelScope.launch { dao.delete(veiculo) }
}