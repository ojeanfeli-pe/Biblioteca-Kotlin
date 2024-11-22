package com.example.controlebiblioteca.screens

import EmprestimoViewModel
import LivroViewModel
import UsuarioViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controlebiblioteca.ui.theme.ControleBibliotecaTheme
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.controlebiblioteca.classes.Livro
import com.example.controlebiblioteca.classes.Usuario

@Composable
fun EmprestimoScreen(
    livroViewModel: LivroViewModel = viewModel(),
    onVoltar: () -> Unit
) {
    // Observar os livros disponíveis
    val livros by livroViewModel.livros.observeAsState(emptyList())

    // Mensagem de erro ou sucesso
    var mensagem by remember { mutableStateOf("") }

    // Ação ao emprestar o livro
    fun emprestarLivro(livro: Livro) {
        if (livro.quantidadeTotal > 0) {
            // Atualiza o estado de disponibilidade do livro e quantidade
            val livroAtualizado = livro.copy(
                quantidadeTotal = livro.quantidadeTotal - 1, // Reduz a quantidade disponível
                disponivel = livro.quantidadeTotal - 1 > 0 // Mantém a disponibilidade se houver mais exemplares
            )
            livroViewModel.adicionarLivro(livroAtualizado) // Atualiza a lista de livros com a mudança
            mensagem = "Empréstimo realizado com sucesso!"
        } else {
            // Exibe mensagem de erro caso não haja exemplares disponíveis
            mensagem = "Não há mais exemplares disponíveis."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Livros Disponíveis para Empréstimo", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Exibe mensagem de erro ou sucesso
        if (mensagem.isNotEmpty()) {
            Text(text = mensagem, color = if (mensagem.contains("não disponível")) Color.Red else Color.Green)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista dos livros disponíveis para empréstimo
        LazyColumn {
            items(livros) { livro ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("${livro.titulo} - ${livro.autor}")
                        Text("Quantidade disponível: ${livro.quantidadeTotal}")
                    }

                    // Botão de empréstimo
                    Button(onClick = { emprestarLivro(livro) }) {
                        if(livro.disponivel == true)
                            Text("Emprestar")

                        else
                            Text("Não Disponível", color = Color.Red)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onVoltar, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Voltar")
        }
    }
}

//@Composable
//fun EmprestimoScreen(
//    livro: Livro,
//    onEmprestimoConcluido: () -> Unit,
//    usuarioViewModel: UsuarioViewModel = viewModel(),
//    emprestimoViewModel: EmprestimoViewModel = viewModel(),
//    navController: NavController
//) {
//    var usuarioSelecionado by remember { mutableStateOf<Usuario?>(null) }
//    var mensagem by remember { mutableStateOf("") }
//
//    // Obtemos a lista de usuários do UsuarioViewModel
//    val usuarios = usuarioViewModel.usuarios
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = "Livro Selecionado: ${livro.titulo}")
//        Text(text = "Autor: ${livro.autor}")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(text = "Selecione um Usuário para Emprestar")
//
//        if (usuarios.isNotEmpty()) {
//            usuarios.forEach { usuario ->
//                Button(onClick = { usuarioSelecionado = usuario }) {
//                    Text("Emprestar para: ${usuario.nome}")
//                }
//            }
//        } else {
//            Text("Nenhum usuário disponível.")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        if (usuarioSelecionado != null) {
//            Button(onClick = {
//                if (livro.disponivel && livro.quantidadeTotal > 0) {
//                    emprestimoViewModel.emprestarLivro(livro, usuarioSelecionado!!, dataEmprestimo = "21/11", dataDevolucao = "22/11")
//                    mensagem = "Empréstimo realizado com sucesso para {$usuarioSelecionado.nome}!"
//                    onEmprestimoConcluido()
//                } else {
//                    mensagem = "Livro não disponível para empréstimo!"
//                }
//            }) {
//                Text(text = "Emprestar Livro")
//            }
//        } else {
//            Text("Selecione um usuário para realizar o empréstimo.", color = Color.Red)
//        }
//    }
//}
