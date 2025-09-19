package com.example.myapplication

import android.content.Context
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource

class VeiculosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaVeiculos()
        }
    }
}

@Composable
fun TelaVeiculos(){

    val context = LocalContext.current

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))){

        TopoVeiculos(context = context)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)){

            Column(modifier = Modifier.padding(16.dp)){

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top){

                    Row(modifier = Modifier.weight(1f)){

                        Image(
                            painter = painterResource(id = R.drawable.carro),
                            contentDescription = "Imagem do carro",
                            modifier = Modifier
                                .size(45.dp)
                                .padding(end = 8.dp))

                    Column (modifier = Modifier.weight(1f)){
                        Text("HONDA/MOTOCICLETA", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        Text("Placa: PPD1377", fontSize = 16.sp, color = Color.DarkGray)
                        Text(text = "Sou Proprietário", fontSize = 18.sp, color = Color.Blue)

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(modifier = Modifier
                                .height(30.dp)
                                .width(60.dp)
                                .background(Color(0xFF388E3C), shape = RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {

                            Text("CRLV", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White) } } }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Abrir",
                        tint = Color.Gray) }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top){

                    Row(modifier = Modifier.weight(1f)){

                        Image(
                            painter = painterResource(id = R.drawable.carro),
                            contentDescription = "Imagem do carro",
                            modifier = Modifier
                                .size(45.dp)
                                .padding(end = 8.dp))

                        Column (modifier = Modifier.weight(1f)){
                            Text("HONDA/FIT CX FLEX", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                            Text("Placa: OXA7777", fontSize = 16.sp, color = Color.DarkGray)
                            Text(text = "Sou Proprietário", fontSize = 18.sp, color = Color.Blue)

                            Spacer(modifier = Modifier.height(4.dp))

                            Box(modifier = Modifier
                                .height(30.dp)
                                .width(60.dp)
                                .background(Color(0xFF388E3C), shape = RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {

                                Text("CRLV", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White) } } }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Abrir",
                        tint = Color.Gray) }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top){

                    Row(modifier = Modifier.weight(1f)){

                        Image(
                            painter = painterResource(id = R.drawable.carro),
                            contentDescription = "Imagem do carro",
                            modifier = Modifier
                                .size(45.dp)
                                .padding(end = 8.dp))

                        Column (modifier = Modifier.weight(1f)){
                            Text("RENAULT/SANDERO EXP 16", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                            Text("Placa: HIU3333", fontSize = 16.sp, color = Color.DarkGray)
                            Text(text = "Compartilhado comigo", fontSize = 18.sp, color = Color.Blue)

                            Spacer(modifier = Modifier.height(4.dp))

                            Box(modifier = Modifier
                                .height(30.dp)
                                .width(60.dp)
                                .background(Color(0xFFFFC107), shape = RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {

                                Text("CRLV", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White) } } }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Abrir",
                        tint = Color.Gray) }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top){

                    Row(modifier = Modifier.weight(1f)){

                        Image(
                            painter = painterResource(id = R.drawable.carro),
                            contentDescription = "Imagem do carro",
                            modifier = Modifier
                                .size(45.dp)
                                .padding(end = 8.dp))

                    Column(modifier = Modifier.weight(1f)){
                        Text("PEUGEOT/208 GRIFFE A", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        Text("Placa: SSS5250", fontSize = 16.sp, color = Color.DarkGray)
                        Text(text = "Último lincenciamento: 2020", fontSize = 18.sp, color = Color.Blue)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top){

                            Box(modifier = Modifier
                                .height(30.dp)
                                .width(60.dp)
                                .background(Color(0xFFD32F2F), shape = RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center){

                                Text("Recall", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)}

                            Spacer(modifier = Modifier.width(5.dp))

                            Box(modifier = Modifier
                                .height(30.dp)
                                .width(60.dp)
                                .background(Color(0xFF388E3C), shape = RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center){

                                Text("CRLV", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)

                            }}}}

                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Abrir",
                        tint = Color.Gray)}
            }
        }

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(6.dp)){

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){

                Image(
                    painter = painterResource(id = R.drawable.file),
                    contentDescription = "Imagem do pdf",
                    modifier = Modifier
                        .size(38.dp)
                        .padding(end = 8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)){

                    Text(
                        text = "CRLV Digital.pdf",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium)

                    Text(
                        text = "108 KB",
                        fontSize = 14.sp,
                        color = Color.Gray) }

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Fechar",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp))
            }
        }

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(6.dp)){

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){

                Image(
                    painter = painterResource(id = R.drawable.docs),
                    contentDescription = "Imagem do documento",
                    modifier = Modifier
                        .size(38.dp)
                        .padding(end = 8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)){

                    Text(
                        text = "assinaturaDigital.p7s",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium)

                    Text(
                        text = "2 KB",
                        fontSize = 14.sp,
                        color = Color.Gray) }

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Fechar",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Composable
fun TopoVeiculos(context: Context){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFF0A3D91))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart){

        Row(verticalAlignment = Alignment.CenterVertically){

            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.White,
                modifier = Modifier.clickable{

                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)})

            Spacer(modifier = Modifier.width(8.dp))

            Text("VEÍCULOS", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TelaVeiculosPreview() {
    TelaVeiculos() }