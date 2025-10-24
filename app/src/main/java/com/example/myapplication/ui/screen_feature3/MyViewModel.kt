package com.example.myapplication.ui.screen_feature3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.InfracaoEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InfracaoViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).infracaoDao()

    val infracoes = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addInfracao(infracao: InfracaoEntity) = viewModelScope.launch {
        dao.insert(infracao)
    }

    fun deleteInfracao(infracao: InfracaoEntity) = viewModelScope.launch {
        dao.delete(infracao)
    }

    fun updateInfracao(infracao: InfracaoEntity) = viewModelScope.launch {
        dao.update(infracao)
    }
}