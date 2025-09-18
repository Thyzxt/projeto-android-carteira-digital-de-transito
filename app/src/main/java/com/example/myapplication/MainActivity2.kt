package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CondutorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaCondutor()
        }
    }
}

@Composable
fun TelaCondutor() {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {

        TopoCondutor(context = context)

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Informações do Condutor", fontSize = 18.sp, fontWeight = FontWeight.Bold)


                Spacer(modifier = Modifier.height(12.dp))


                InfoLinha("Nome", "*******")
                InfoLinha("CPF", "*******", "Sexo", "********")
                InfoLinha("Categoria", "B", "UF de Emissão", "PR")
                InfoLinha("Data de Validade", "03/11/2075",
                    "Data de Emissão", "03/11/2055")
            }
        }


        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CardBotao("HABILITAÇÃO", Icons.Default.AccountBox)
                CardBotao("CADASTRO POSITIVO", Icons.Default.Person)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CardBotao("EXAMES TOXICOLÓGICOS", Icons.Default.Warning)
                CardBotao("CURSOS ESPECIALIZADOS", Icons.Default.Menu)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CardBotao("CREDENCIAL DE ESTACIONAMENTO", Icons.Default.Star)
            }
        }
    }
}


@Composable
fun TopoCondutor(context: Context) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFF0A3D91))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.White,
                modifier = Modifier.clickable {

                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("CONDUTOR", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun InfoLinha(label1: String, valor1: String, label2: String? = null, valor2: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(label1, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(valor1, fontSize = 14.sp)
        }
        if (label2 != null && valor2 != null) {
            Column {
                Text(label2, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(valor2, fontSize = 14.sp)
            }
        }
    }
}


@Composable
fun CardBotao(texto: String, icone: ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(6.dp)
            .size(width = 150.dp, height = 100.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icone,
                contentDescription = texto,
                tint = Color(0xFF0A3D91),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                texto,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0A3D91),
                maxLines = 2
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TelaCondutorPreview() {
    TelaCondutor()
}
