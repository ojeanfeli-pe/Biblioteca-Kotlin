package com.example.controlebiblioteca.screens

import LivroViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.controlebiblioteca.classes.Livro

@Composable
fun GerenciamentoDeLivrosScreen(
    livroViewModel: LivroViewModel = viewModel(),
    onVoltar: () -> Unit
) {
    var tituloBusca by remember { mutableStateOf("") }

    // Observa a LiveData corretamente
    val livros by livroViewModel.livros.observeAsState(emptyList())

    val livrosFiltrados = livros.filter {
        it.titulo.contains(tituloBusca, ignoreCase = true) ||
                it.autor.contains(tituloBusca, ignoreCase = true)
    }

    var livroSelecionado by remember { mutableStateOf<Livro?>(null) }
    var mostrarAdicionarLivroDialog by remember { mutableStateOf(false) }

    // Estados do formulário de adição de livro
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var anoPublicacao by remember { mutableStateOf("") }
    var erroAno by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Gerenciamento de Livros",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = tituloBusca,
            onValueChange = { tituloBusca = it },
            label = { Text("Buscar por título ou autor") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(livrosFiltrados) { livro ->
                LivroItem(
                    livro = livro,
                    onEditar = { livroSelecionado = livro },
                    onRemover = { livroViewModel.removerLivro(livro) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onVoltar, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Voltar")
        }

        // Botão para adicionar livro
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { mostrarAdicionarLivroDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Adicionar Livro")
        }
    }

    // Dialog para adicionar livro
    if (mostrarAdicionarLivroDialog) {
        AlertDialog(
            onDismissRequest = { mostrarAdicionarLivroDialog = false },
            title = { Text("Adicionar Livro") },
            text = {
                Column {
                    TextField(
                        value = titulo,
                        onValueChange = { titulo = it.trim() },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = autor,
                        onValueChange = { autor = it.trim() },
                        label = { Text("Autor") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = anoPublicacao,
                        onValueChange = {
                            anoPublicacao = it.trim()
                            erroAno = anoPublicacao.toIntOrNull() == null
                        },
                        label = { Text("Ano de Publicação") },
                        isError = erroAno,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (erroAno) {
                        Text("Por favor, insira um ano válido.", color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (!erroAno && titulo.isNotBlank() && autor.isNotBlank()) {
                            val livro = Livro(
                                id = 0, // O banco de dados gera o ID automaticamente
                                titulo = titulo,
                                autor = autor,
                                anoPublicacao = anoPublicacao.toInt(),
                                disponivel = true,
                                categoria = "Gênero Padrão",
                                quantidadeTotal = 1
                            )
                            livroViewModel.adicionarLivro(livro)
                            mostrarAdicionarLivroDialog = false
                        }
                    },
                    enabled = titulo.isNotBlank() && autor.isNotBlank() && !erroAno
                ) {
                    Text("Adicionar Livro")
                }
            },
            dismissButton = {
                Button(onClick = { mostrarAdicionarLivroDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (livroSelecionado != null) {
        EditarLivroDialog(
            livro = livroSelecionado!!,
            onSalvar = { livro ->
                livroViewModel.editarLivro(livro)
                livroSelecionado = null
            },
            onCancelar = { livroSelecionado = null }
        )
    }
}

@Composable
fun LivroItem(
    livro: Livro,
    onEditar: (Livro) -> Unit,
    onRemover: (Livro) -> Unit
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
                Text(text = "Título: ${livro.titulo}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Autor: ${livro.autor}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Ano: ${livro.anoPublicacao}", style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = { onEditar(livro) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                }
                IconButton(onClick = { onRemover(livro) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remover"
                    )
                }
            }
        }
    }
}

@Composable
fun EditarLivroDialog(
    livro: Livro,
    onSalvar: (Livro) -> Unit,
    onCancelar: () -> Unit
) {
    var titulo by remember { mutableStateOf(livro.titulo) }
    var autor by remember { mutableStateOf(livro.autor) }
    var anoPublicacao by remember { mutableStateOf(livro.anoPublicacao.toString()) }
    var erroAno by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("Editar Livro") },
        text = {
            Column {
                TextField(
                    value = titulo,
                    onValueChange = { titulo = it.trim() },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = autor,
                    onValueChange = { autor = it.trim() },
                    label = { Text("Autor") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = anoPublicacao,
                    onValueChange = {
                        anoPublicacao = it.trim()
                        erroAno = anoPublicacao.toIntOrNull() == null
                    },
                    label = { Text("Ano de Publicação") },
                    isError = erroAno,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                if (erroAno) {
                    Text("Por favor, insira um ano válido.", color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (!erroAno) {
                        onSalvar(
                            livro.copy(
                                titulo = titulo,
                                autor = autor,
                                anoPublicacao = anoPublicacao.toInt()
                            )
                        )
                    }
                },
                enabled = titulo.isNotBlank() && autor.isNotBlank() && !erroAno
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}