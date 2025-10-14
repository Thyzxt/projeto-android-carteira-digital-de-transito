package com.example.myapplication

import android.app.Application
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Status {
    VENCIDA,
    A_VENCER,
    PAGA
}

@Entity(tableName = "infracoes")
data class InfracaoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val descricao: String,
    val data: String,
    val valor: Double,
    val status: Status
)

class Converters {
    @TypeConverter
    fun fromStatus(status: Status): String = status.name

    @TypeConverter
    fun toStatus(value: String): Status = Status.valueOf(value)
}

@Dao
interface InfracaoDao {
    @Query("SELECT * FROM infracoes")
    fun getAll(): kotlinx.coroutines.flow.Flow<List<InfracaoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(infracao: InfracaoEntity)

    @Delete
    suspend fun delete(infracao: InfracaoEntity)

    @Update
    suspend fun update(infracao: InfracaoEntity)
}

@Database(entities = [InfracaoEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun infracaoDao(): InfracaoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "infracoes_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class InfracaoViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).infracaoDao()
    private val prefs = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val infracoes = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun adicionarIniciais() {
        viewModelScope.launch {
            val jaInicializado = prefs.getBoolean("infracoes_iniciais_inseridas", false)
            if (!jaInicializado) {
                val iniciais = listOf(
                    InfracaoEntity(descricao = "AVANÇAR O SINAL VERMELHO DO SEMÁFORO", data = "10/09/2025 - 17:58", valor = 195.23, status = Status.VENCIDA),
                    InfracaoEntity(descricao = "DIRIGIR SEM CINTO DE SEGURANÇA", data = "23/03/2025 - 14:35", valor = 153.72, status = Status.VENCIDA),
                    InfracaoEntity(descricao = "USAR CELULAR AO DIRIGIR", data = "27/11/2025 - 13:31", valor = 293.47, status = Status.A_VENCER),
                    InfracaoEntity(descricao = "DIRIGIR SEM CINTO DE SEGURANÇA", data = "10/07/2023 - 23:52", valor = 293.47, status = Status.PAGA)
                )
                iniciais.forEach { dao.insert(it) }

                prefs.edit().putBoolean("infracoes_iniciais_inseridas", true).apply()
            }
        }
    }

    fun addInfracao(infracao: InfracaoEntity) = viewModelScope.launch {
        dao.insert(infracao)
    }

    fun deleteInfracao(infracao: InfracaoEntity) = viewModelScope.launch {
        dao.delete(infracao)
    }

    fun updateInfracao(infracao: InfracaoEntity) = viewModelScope.launch {
        dao.update(infracao)
    }

    fun resetarBanco() = viewModelScope.launch {
        prefs.edit().remove("infracoes_iniciais_inseridas").apply()
    }
}

class InfratorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaInfrator()
        }
    }
}

@Composable
fun TelaInfrator(viewModel: InfracaoViewModel = viewModel()) {
    val context = LocalContext.current
    val infracoes by viewModel.infracoes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.adicionarIniciais()
    }

    val vencidas = infracoes.filter { it.status == Status.VENCIDA }
    val aVencer = infracoes.filter { it.status == Status.A_VENCER }
    val pagas = infracoes.filter { it.status == Status.PAGA }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        TopoInfrator(context = context)

        InfracaoSection("Vencidas", vencidas, onDelete = { viewModel.deleteInfracao(it) })
        InfracaoSection("A vencer", aVencer, onUpdate = {
            viewModel.updateInfracao(it.copy(status = Status.PAGA))
        })
        InfracaoSection("Pagas", pagas, onDelete = { viewModel.deleteInfracao(it) })

        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Adicionar")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Adicionar Infração")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                InfBotao("SOLICITAR BOLETO", Icons.Default.Person)
                InfBotao("SITUAÇÃO DO INFRATOR", Icons.Default.Info)
            }
        }

        if (showDialog) {
            AdicionarInfracaoDialog(
                onDismiss = { showDialog = false },
                onAdd = { descricao, data, valorStr, status ->
                    val valor = valorStr.toDoubleOrNull()
                    if (descricao.isNotBlank() && data.isNotBlank() && valor != null) {
                        viewModel.addInfracao(
                            InfracaoEntity(descricao = descricao, data = data, valor = valor, status = status)
                        )
                        showDialog = false
                    }
                }
            )
        }
    }
}

@Composable
fun TopoInfrator(context: Context) {
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
            Text("INFRAÇÕES", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun InfracaoSection(
    titulo: String,
    lista: List<InfracaoEntity>,
    onDelete: ((InfracaoEntity) -> Unit)? = null,
    onUpdate: ((InfracaoEntity) -> Unit)? = null
) {
    if (lista.isEmpty()) return
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("$titulo (${lista.size})", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))
            lista.forEach { infracao ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(infracao.descricao, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Text(infracao.data, fontSize = 16.sp, color = Color.DarkGray)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("R$ %.2f".format(infracao.valor), fontSize = 16.sp, color = Color.Red)
                            Spacer(modifier = Modifier.width(60.dp))
                            Text(
                                "Emissão de boleto",
                                fontSize = 14.sp,
                                color = Color(0xFF006400),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        onUpdate?.let {
                            IconButton(onClick = { it(infracao) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Atualizar")
                            }
                        }
                        onDelete?.let {
                            IconButton(onClick = { it(infracao) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Deletar", tint = Color.Red)
                            }
                        }
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Abrir multa", tint = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun AdicionarInfracaoDialog(
    onDismiss: () -> Unit,
    onAdd: (descricao: String, data: String, valor: String, status: Status) -> Unit
) {
    var descricao by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(Status.VENCIDA) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Nova Infração") },
        text = {
            Column {
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = data,
                    onValueChange = { data = it },
                    label = { Text("Data (dd/MM/yyyy - HH:mm)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = valor,
                    onValueChange = { valor = it },
                    label = { Text("Valor (ex: 195.23)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Status:")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Status.values().forEach { st ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = (status == st), onClick = { status = st })
                            Text(st.name)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onAdd(descricao, data, valor, status) }) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
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
            Icon(icone, contentDescription = texto, tint = Color.White, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(texto, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White, maxLines = 2)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TelaInfratorPreview() {
    TelaInfrator()
}


