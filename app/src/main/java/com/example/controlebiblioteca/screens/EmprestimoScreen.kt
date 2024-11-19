package com.example.controlebiblioteca.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.controlebiblioteca.classes.Livro
import com.example.controlebiblioteca.classes.Usuario


@Composable
fun EmprestimoScreen(
    livro: Livro,
    livroViewModel: LivroViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel(),
    onEmprestimoConcluido: () -> Unit
) {
    var usuarioSelecionado by remember { mutableStateOf<Usuario?>(null) }
    var mensagem by remember { mutableStateOf("") }

    // Obtemos a lista de usuários do UsuarioViewModel
    val usuarios = usuarioViewModel.usuarios

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Livro Selecionado: ${livro.titulo}")
        Text(text = "Autor: ${livro.autor}")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Selecione um Usuário para Emprestar")

        if (usuarios.isNotEmpty()) {
            usuarios.forEach { usuario ->
                Button(onClick = { usuarioSelecionado = usuario }) {
                    Text("Emprestar para: ${usuario.nome}")
                }
            }
        } else {
            Text("Nenhum usuário disponível.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (usuarioSelecionado != null) {
            Button(onClick = {
                // Verifica se o livro está disponível
                if (livro.disponivel && livro.quantidadeTotal > 0) {
                    // Atualiza o livro e marca como emprestado
                    livroViewModel.emprestarLivro(livro)
                    mensagem = "Empréstimo realizado com sucesso para ${usuarioSelecionado?.nome}!"
                    onEmprestimoConcluido() // Volta para a tela anterior ou outra ação
                } else {
                    mensagem = "Livro não disponível para empréstimo!"
                }
            }) {
                Text(text = "Emprestar Livro")
            }
        } else {
            Text("Selecione um usuário para realizar o empréstimo.", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mensagem.isNotEmpty()) {
            Text(text = mensagem, color = Color.Green)
        }
    }
}
