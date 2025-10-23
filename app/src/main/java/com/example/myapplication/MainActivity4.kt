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

// -------------------- DATA CLASS PARA COMPOSABLES E PREVIEW --------------------
data class Veiculo(
    val modelo: String,
    val placa: String,
    val descricao: String,
    val statusColor: Color
)

// -------------------- ENTIDADE E ROOM --------------------
@Entity(tableName = "veiculos")
data class VeiculoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val modelo: String,
    val placa: String,
    val descricao: String,
    val statusColor: Long // Salva cor como Long (ARGB)
)

@Dao
interface VeiculoDao {
    @Query("SELECT * FROM veiculos")
    fun getAll(): kotlinx.coroutines.flow.Flow<List<VeiculoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(veiculo: VeiculoEntity)

    @Update
    suspend fun update(veiculo: VeiculoEntity)

    @Delete
    suspend fun delete(veiculo: VeiculoEntity)
}

// -------------------- VIEWMODEL --------------------
class VeiculoViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).veiculoDao()

    val veiculos = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addVeiculo(veiculo: VeiculoEntity) = viewModelScope.launch { dao.insert(veiculo) }
    fun updateVeiculo(veiculo: VeiculoEntity) = viewModelScope.launch { dao.update(veiculo) }
    fun deleteVeiculo(veiculo: VeiculoEntity) = viewModelScope.launch { dao.delete(veiculo) }
}

// -------------------- ACTIVITY --------------------
class VeiculosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaVeiculos()
        }
    }
}

// -------------------- COMPOSABLES --------------------
@Composable
fun TelaVeiculos(viewModel: VeiculoViewModel = viewModel()) {
    val context = LocalContext.current
    val veiculos by viewModel.veiculos.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var editVeiculo by remember { mutableStateOf<VeiculoEntity?>(null) }

    // Campos temporários do diálogo
    var modelo by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var statusColor by remember { mutableStateOf(Color(0xFF388E3C)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        TopoVeiculos(context = context)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                veiculos.forEach { veiculoEntity ->
                    val veiculo = Veiculo(
                        veiculoEntity.modelo,
                        veiculoEntity.placa,
                        veiculoEntity.descricao,
                        Color(veiculoEntity.statusColor)
                    )
                    VeiculoItem(
                        veiculo = veiculo,
                        onEdit = {
                            modelo = veiculo.modelo
                            placa = veiculo.placa
                            descricao = veiculo.descricao
                            statusColor = veiculo.statusColor
                            editVeiculo = veiculoEntity
                            showDialog = true
                        },
                        onDelete = { viewModel.deleteVeiculo(veiculoEntity) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = {
                        modelo = ""
                        placa = ""
                        descricao = ""
                        statusColor = Color(0xFF388E3C)
                        editVeiculo = null
                        showDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Adicionar veículo")
                }
            }
        }

        ArquivosExtras()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (modelo.isNotBlank() && placa.isNotBlank()) {
                        val novo = VeiculoEntity(
                            id = editVeiculo?.id ?: 0,
                            modelo = modelo,
                            placa = placa,
                            descricao = descricao,
                            statusColor = statusColor.value.toLong()
                        )
                        if (editVeiculo != null) {
                            viewModel.updateVeiculo(novo)
                        } else {
                            viewModel.addVeiculo(novo)
                        }
                        showDialog = false
                    }
                }) { Text("Salvar") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
            },
            title = { Text(if (editVeiculo != null) "Editar veículo" else "Novo veículo") },
            text = {
                Column {
                    OutlinedTextField(value = modelo, onValueChange = { modelo = it }, label = { Text("Modelo") })
                    OutlinedTextField(value = placa, onValueChange = { placa = it }, label = { Text("Placa") })
                    OutlinedTextField(value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") })
                }
            }
        )
    }
}

@Composable
fun VeiculoItem(veiculo: Veiculo, onEdit: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(id = R.drawable.carro),
                contentDescription = "Imagem do carro",
                modifier = Modifier
                    .size(45.dp)
                    .padding(end = 8.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(veiculo.modelo, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Text("Placa: ${veiculo.placa}", fontSize = 16.sp, color = Color.DarkGray)
                Text(veiculo.descricao, fontSize = 16.sp, color = Color.Blue)
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(60.dp)
                        .background(veiculo.statusColor, shape = RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("CRLV", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar",
                tint = Color.Gray,
                modifier = Modifier
                    .clickable { onEdit() }
                    .padding(bottom = 4.dp)
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Excluir",
                tint = Color.Red,
                modifier = Modifier.clickable { onDelete() }
            )
        }
    }
}

@Composable
fun ArquivosExtras() {
    listOf(
        Pair("CRLV Digital.pdf", "108 KB") to R.drawable.file,
        Pair("assinaturaDigital.p7s", "2 KB") to R.drawable.docs
    ).forEach {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = it.second),
                    contentDescription = "Arquivo",
                    modifier = Modifier.size(38.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(it.first.first, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text(it.first.second, fontSize = 14.sp, color = Color.Gray)
                }
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Fechar",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun TopoVeiculos(context: Context) {
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
            Text("VEÍCULOS", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

// -------------------- PREVIEW --------------------
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
