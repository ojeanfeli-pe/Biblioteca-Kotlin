package com.example.controlebiblioteca.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import UsuarioViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.controlebiblioteca.classes.Usuario

@Composable
fun GerenciamentoDeUsuariosScreen(
    usuarioViewModel: UsuarioViewModel = viewModel(),
    onVoltar: () -> Unit
) {

    var nomeBusca by remember { mutableStateOf("") }
    val usuarios by usuarioViewModel.usuarios.observeAsState(emptyList())
    val usuariosFiltrados = usuarios.filter { it.nome.contains(nomeBusca, ignoreCase = true) }

    var mostrarAdicionarUsuarioDialog by remember { mutableStateOf(false) }

    // Estados do formulário
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var tipoUsuario by remember { mutableStateOf("") }
    var erroCampos by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Gerenciamento de Usuários",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nomeBusca,
            onValueChange = { nomeBusca = it },
            label = { Text("Buscar por nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(usuariosFiltrados) { usuario ->
                UsuarioItem(
                    usuario = usuario,
                    onRemover = { usuarioViewModel.removerUsuario(usuario) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onVoltar, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Voltar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { mostrarAdicionarUsuarioDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Adicionar Usuário")
        }
    }

    if (mostrarAdicionarUsuarioDialog) {
        AlertDialog(
            onDismissRequest = { mostrarAdicionarUsuarioDialog = false },
            title = { Text("Adicionar Usuário") },
            text = {

                Column {

                    TextField(
                        value = nome,
                        onValueChange = { nome = it.trim() },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = email,
                        onValueChange = { email = it.trim() },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = telefone,
                        onValueChange = { telefone = it.trim() },
                        label = { Text("Telefone (opcional)") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = tipoUsuario,
                        onValueChange = { tipoUsuario = it.trim() },
                        label = { Text("Tipo de Usuário (funcionario ou usuario)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (erroCampos) {
                        Text(
                            "Por favor, preencha todos os campos obrigatórios.",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nome.isNotBlank() && email.isNotBlank() && tipoUsuario.isNotBlank()) {
                            val usuario = Usuario(
                                nome = nome,
                                email = email,
                                telefone = telefone.ifBlank { null },
                                tipoUsuario = tipoUsuario,
                                dataCadastro = System.currentTimeMillis()
                            )
                            usuarioViewModel.adicionarUsuario(usuario)
                            mostrarAdicionarUsuarioDialog = false
                            erroCampos = false
                        } else {
                            erroCampos = true
                        }
                    }
                ) {
                    Text("Adicionar")
                }
            },
            dismissButton = {
                Button(onClick = { mostrarAdicionarUsuarioDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun UsuarioItem(
    usuario: Usuario,
    onRemover: (Usuario) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Nome: ${usuario.nome}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Email: ${usuario.email}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Tipo: ${usuario.tipoUsuario}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Telefone: ${usuario.telefone ?: "Não informado"}", style = MaterialTheme.typography.bodySmall)
            }

            IconButton(onClick = { onRemover(usuario) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remover"
                )
            }
        }
    }
}
