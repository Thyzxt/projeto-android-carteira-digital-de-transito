package com.example.myapplication

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.*
import com.example.myapplication.data.local.AppDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.myapplication.data.local.InfracaoEntity
import com.example.myapplication.data.local.InfracaoDao
import com.example.myapplication.data.local.Status
import com.example.myapplication.data.local.VeiculoEntity
import com.example.myapplication.ui.screen_feature4.ArquivosExtras
import com.example.myapplication.ui.screen_feature4.TelaVeiculos
import com.example.myapplication.ui.screen_feature4.TopoVeiculos
import com.example.myapplication.ui.screen_feature4.VeiculoItem

data class Veiculo(
    val modelo: String,
    val placa: String,
    val descricao: String,
    val statusColor: Color
)

class VeiculosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaVeiculos()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TelaVeiculosPreview() {
    val mockVeiculos = listOf(
        Veiculo("HONDA/MOTOCICLETA", "PPD1377", "Sou Proprietário", Color(0xFF388E3C)),
        Veiculo("HONDA/FIT CX FLEX", "OXA7777", "Sou Proprietário", Color(0xFF388E3C)),
        Veiculo("RENAULT/SANDERO EXP 16", "HIU3333", "Compartilhado comigo", Color(0xFFFFC107)),
        Veiculo("PEUGEOT/208 GRIFFE A", "SSS5250", "Último licenciamento: 2020", Color(0xFFD32F2F))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        TopoVeiculos(context = LocalContext.current)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                mockVeiculos.forEach { veiculo ->
                    VeiculoItem(veiculo, onEdit = {}, onDelete = {})
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        ArquivosExtras()
    }
}
