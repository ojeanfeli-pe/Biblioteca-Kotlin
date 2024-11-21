package com.example.controlebiblioteca.screens

import LivroViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
        TextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = autor, onValueChange = { autor = it }, label = { Text("Autor") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = anoPublicacao, onValueChange = { anoPublicacao = it }, label = { Text("Ano de Publicação") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val livro = Livro(
                id = 1, // Id é um exemplo, você pode adaptar conforme necessário
                titulo = titulo,
                autor = autor,
                anoPublicacao = anoPublicacao.toInt(),
                disponivel = true,
                categoria = "Gênero Padrão",
                quantidadeTotal = 1
            )
            livroViewModel.adicionarLivro(livro)
            onLivroAdicionado() // Volta para a tela anterior após adicionar o livro
        }) {
            Text("Adicionar Livro")
        }
    }
}
