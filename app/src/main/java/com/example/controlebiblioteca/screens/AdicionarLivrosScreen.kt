package com.example.controlebiblioteca.screens

import LivroViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.controlebiblioteca.classes.Livro

@Composable
fun AdicionarLivroScreen(
    livroViewModel: LivroViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLivroAdicionado: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var anoPublicacao by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = autor,
            onValueChange = { autor = it },
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = anoPublicacao,
            onValueChange = { anoPublicacao = it },
            label = { Text("Ano de Publicação") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val livro = Livro(
                    id = 0, // Deixe o banco gerar o ID automaticamente
                    titulo = titulo,
                    autor = autor,
                    anoPublicacao = anoPublicacao.toIntOrNull() ?: 0, // Trata possíveis valores inválidos
                    disponivel = true,
                    categoria = "Gênero Padrão",
                    quantidadeTotal = 1
                )
                livroViewModel.adicionarLivro(livro)
                onLivroAdicionado() // Retorna para a tela anterior após adicionar
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Livro")
        }
    }
}
