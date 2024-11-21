package com.example.controlebiblioteca.screens

import LivroViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.controlebiblioteca.classes.Livro

@Composable
fun AdicionarLivroScreen(
    livroViewModel: LivroViewModel = viewModel(),
    onLivroAdicionado: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var anoPublicacao by remember { mutableStateOf("") }
    var quantidadeTotal by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf("") }

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
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = quantidadeTotal, onValueChange = { quantidadeTotal = it }, label = { Text("Quantidade Total") })
        Spacer(modifier = Modifier.height(16.dp))

        if (mensagemErro.isNotEmpty()) {
            Text(text = mensagemErro)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(onClick = {
            val ano = anoPublicacao.toIntOrNull()
            val quantidade = quantidadeTotal.toIntOrNull()
            if (titulo.isEmpty() || autor.isEmpty() || ano == null || quantidade == null) {
                mensagemErro = "Preencha todos os campos corretamente!"
            } else {
                val livro = Livro(
                    id = livroViewModel.livros.size + 1,
                    titulo = titulo,
                    autor = autor,
                    anoPublicacao = ano,
                    disponivel = true,
                    categoria = "Gênero Padrão",
                    quantidadeTotal = quantidade
                )
                livroViewModel.adicionarLivro(livro)
                onLivroAdicionado()
            }
        }) {
            Text("Adicionar Livro")
        }
    }
}
