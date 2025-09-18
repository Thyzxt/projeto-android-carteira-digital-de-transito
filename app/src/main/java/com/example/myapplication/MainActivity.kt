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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaPrincipal()
        }
    }
}


@Composable
fun TelaPrincipal() {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        TopAppBar()
        Spacer(modifier = Modifier.height(16.dp))

        CardOpcao(
            titulo = "CONDUTOR",
            descricao = "Gerencie sua habilitação",
            cor = Color(0xFF2ECC71),
            icone = Icons.Default.Person,
            onClick = {

                val intent = Intent(context, CondutorActivity::class.java)
                context.startActivity(intent)
            }
        )
        CardOpcao(
            titulo = "VEÍCULOS",
            descricao = "Acesso ao CRLV-e, venda digital",
            cor = Color(0xFFF1C40F),
            icone = Icons.Default.Star,
            onClick = {

                val intent = Intent(context, CondutorActivity::class.java)
                context.startActivity(intent)
            }
        )
        CardOpcao(
            titulo = "INFRAÇÕES",
            descricao = "Visualize e pague infrações\ncom até 40% de desconto",
            cor = Color(0xFF1E3799),
            icone = Icons.Default.Clear,
            onClick = {

                val intent = Intent(context, InfratorActivity::class.java)
                context.startActivity(intent)
            }
        )
        CardOpcao(
            titulo = "EDUCAÇÃO",
            descricao = "Conheça nossas campanhas e projetos",
            cor = Color(0xFF3498DB),
            icone = Icons.Default.AccountBox
        )
    }
}

@Composable
fun TopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(0xFF0A3D91))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
        Text(
            text = "RAFAEL",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(start = 15.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.size(46.dp)
                .padding(end = 16.dp)

        )
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
fun CardOpcao(
    titulo: String,
    descricao: String,
    cor: Color,
    icone: ImageVector,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick ?: {}),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = titulo, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = descricao, fontSize = 14.sp, color = Color.White)
            }
            Icon(
                imageVector = icone,
                contentDescription = titulo,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TelaPrincipalPreview() {
    TelaPrincipal()
}

