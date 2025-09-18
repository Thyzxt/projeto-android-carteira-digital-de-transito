package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Spacer
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext

class InfratorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaInfrator()
        }
    }
}

@Composable
fun TelaInfrator(){

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))){

        TopoInfrator()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)){

            Column(modifier = Modifier.padding(16.dp)){

                Text("Vencidas (2)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top){

                    Column (modifier = Modifier.weight(1f)){
                        Text("AVANÇAR O SINAL VERMELHO DO SEMAFARO", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Text("10/09/2025 - 17:58", fontSize = 16.sp, color = Color.DarkGray)

                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(text = "R$ 195,23", fontSize = 16.sp, color = Color.Red)

                            Spacer(modifier = Modifier.width(90.dp))

                            Text(text = "Emissão de boleto", fontSize = 14.sp, color = Color(0xFF006400), fontWeight = FontWeight.Medium)} }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Abrir multa",
                        tint = Color.Gray) }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top){

                    Column(modifier = Modifier.weight(1f)){
                        Text("DIRIGIR VEÍCULO SEM USAR O CINTO DE SEGURANÇA", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Text("23/03/2025 - 14:35", fontSize = 16.sp, color = Color.DarkGray)
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(text = "R$ 153,72", fontSize = 16.sp, color = Color.Red)

                            Spacer(modifier = Modifier.width(90.dp))

                            Text(text = "Emissão de boleto", fontSize = 14.sp, color = Color(0xFF006400), fontWeight = FontWeight.Medium)}}

                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "Abrir multa",
                        tint = Color.Gray)}
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)){

            Column(modifier = Modifier.padding(16.dp)){

                Text("A vencer (1)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top){

                    Column (modifier = Modifier.weight(1f)){
                        Text("DIRIGIR VEÍCULO SEGURANDO O TELEFONE CELULAR", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Text("27/11/2025 - 13:31", fontSize = 16.sp, color = Color.DarkGray)

                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(text = "R$ 293,47", fontSize = 16.sp, color = Color.Red)

                            Spacer(modifier = Modifier.width(90.dp))

                            Text(text = "Emissão de boleto", fontSize = 14.sp, color = Color(0xFF006400), fontWeight = FontWeight.Medium)} }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Abrir multa",
                        tint = Color.Gray)}
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)){

            Column(modifier = Modifier.padding(16.dp)){

                Text("Pagas (1)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top){

                    Column (modifier = Modifier.weight(1f)){
                        Text("DIRIGIR VEÍCULO SEM USAR O CINTO DE SEGURANÇA", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Text("10/07/2023 - 23:52", fontSize = 16.sp, color = Color.DarkGray)

                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(text = "R$ 293,47", fontSize = 16.sp, color = Color.Red)}}

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Abrir multa",
                        tint = Color.Gray)}
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                InfBotao("SOLICITAR BOLETO", Icons.Default.Person)
                InfBotao("SITUAÇÃO DO INFRATOR", Icons.Default.Info)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                InfBotao("INFORMAR REAL INFRATOR", Icons.Default.Edit)
                InfBotao("PDF DA INFRAÇÃO", Icons.Default.CheckCircle)
            }
        }
    }
}

@Composable
fun TopoInfrator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFF0A3D91))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("INFRAÇÕES", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun InfBotao(texto: String, icone: ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(6.dp)
            .size(width = 165.dp, height = 60.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A3D91))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                icone,
                contentDescription = texto,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                texto,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                maxLines = 2
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TelaInfratorPreview() {
    TelaInfrator()
}

