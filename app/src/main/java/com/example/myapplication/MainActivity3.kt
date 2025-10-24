package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.data.local.InfracaoEntity
import com.example.myapplication.data.local.Status
import com.example.myapplication.ui.screen_feature3.InfracaoSection
import com.example.myapplication.ui.screen_feature3.TelaInfrator
import com.example.myapplication.ui.screen_feature3.TopoInfrator

class InfratorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaInfrator()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TelaInfratorPreview() {

    val mockInfracoes = listOf(
        InfracaoEntity(descricao = "AVANÇAR O SINAL VERMELHO DO SEMÁFORO", data = "10/09/2025 - 17:58", valor = 195.23, status = Status.VENCIDA),
        InfracaoEntity(descricao = "DIRIGIR SEM CINTO DE SEGURANÇA", data = "23/03/2025 - 14:35", valor = 153.72, status = Status.VENCIDA),
        InfracaoEntity(descricao = "USAR CELULAR AO DIRIGIR", data = "27/11/2025 - 13:31", valor = 293.47, status = Status.A_VENCER),
        InfracaoEntity(descricao = "USAR CELULAR AO DIRIGIR", data = "10/07/2023 - 23:52", valor = 293.47, status = Status.PAGA)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        TopoInfrator(context = LocalContext.current)
        InfracaoSection("Vencidas", mockInfracoes.filter { it.status == Status.VENCIDA })
        InfracaoSection("A vencer", mockInfracoes.filter { it.status == Status.A_VENCER })
        InfracaoSection("Pagas", mockInfracoes.filter { it.status == Status.PAGA })
    }
}



