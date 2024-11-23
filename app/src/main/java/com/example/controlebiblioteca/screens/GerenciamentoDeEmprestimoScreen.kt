package com.example.controlebiblioteca.screens

import LivroViewModel
import UsuarioViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.controlebiblioteca.classes.Livro
import com.example.controlebiblioteca.classes.Relatorio
import com.example.controlebiblioteca.classes.Usuario
import com.example.controlebiblioteca.viewmodels.RelatorioViewModel


@Composable
fun GerenciamentoDeEmprestimoScreen(
    livroViewModel: LivroViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel(),
    relatorioViewModel: RelatorioViewModel = viewModel(),
    onVoltar: () -> Unit
) {
    // Observar dados
    val livros by livroViewModel.livros.observeAsState(emptyList())
    val usuarios by usuarioViewModel.usuarios.observeAsState(emptyList())


    var mensagem by remember { mutableStateOf("") }
    var livroSelecionadoParaAcao by remember { mutableStateOf<Livro?>(null) }

    var mostrarEmprestimoUsu by remember { mutableStateOf(false) }
    var mostrarDevolucaoUsu by remember { mutableStateOf(false) }

    fun emprestarLivro(livro: Livro, usuario: Usuario) {
        if (livro.quantidadeTotal > 0) {
            val livroAtualizado = livro.copy(
                quantidadeTotal = livro.quantidadeTotal - 1,
                disponivel = livro.quantidadeTotal - 1 > 0
            )
            livroViewModel.adicionarLivro(livroAtualizado)

            // Salvar no relatório
            val relatorio = Relatorio(
                acao = "Empréstimo",
                livroTitulo = livro.titulo,
                usuarioNome = usuario.nome,
                dataHora = System.currentTimeMillis()
            )
            relatorioViewModel.adicionarRelatorio(relatorio)

            mensagem = "Empréstimo de '${livro.titulo}' realizado para '${usuario.nome}' com sucesso!"
        } else {
            mensagem = "Não há mais exemplares disponíveis."
        }
    }

    fun devolverLivro(livro: Livro, usuario: Usuario) {
        //fiz esse if somente pra quebrar um galho
        if (livro.quantidadeTotal == 0){
        val livroAtualizado = livro.copy(
            quantidadeTotal = livro.quantidadeTotal + 1,
            disponivel = true
        )
        livroViewModel.adicionarLivro(livroAtualizado)

            // Salvar no relatório
            val relatorio = Relatorio(
                acao = "Devolução",
                livroTitulo = livro.titulo,
                usuarioNome = usuario.nome,
                dataHora = System.currentTimeMillis()
            )
            relatorioViewModel.adicionarRelatorio(relatorio)

        mensagem = "Devolução de '${livro.titulo}' pelo usuário '${usuario.nome}' realizada com sucesso!"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "Livros Disponíveis", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Exibe mensagem de sucesso ou erro
        if (mensagem.isNotEmpty()) {
            Text(
                text = mensagem,
                color = if (mensagem.contains("não")) Color.Red else Color.Green
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de livros
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
                    Button(onClick = {
                        livroSelecionadoParaAcao = livro
                        mostrarEmprestimoUsu = true
                    }) {
                        Text("Emprestar")
                    }

                    // Botão de devolução
                    Button(onClick = {
                        livroSelecionadoParaAcao = livro
                        mostrarDevolucaoUsu = true
                    }) {
                        Text("Devolver")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onVoltar, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Voltar")
        }
    }

    // Diálogo para selecionar usuário no empréstimo
    if (mostrarEmprestimoUsu && livroSelecionadoParaAcao != null) {
        AlertDialog(
            onDismissRequest = {
                mostrarEmprestimoUsu = false
                livroSelecionadoParaAcao = null
            },
            title = { Text("Selecionar Usuário para Empréstimo") },
            text = {
                LazyColumn {
                    items(usuarios) { usuario ->
                        DropdownMenuItem(
                            text = { Text(usuario.nome) },
                            onClick = {
                                emprestarLivro(livroSelecionadoParaAcao!!, usuario)
                                mostrarEmprestimoUsu = false
                                livroSelecionadoParaAcao = null
                            }
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarEmprestimoUsu = false
                        livroSelecionadoParaAcao = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo para selecionar usuário na devolução
    if (mostrarDevolucaoUsu && livroSelecionadoParaAcao != null) {
        AlertDialog(
            onDismissRequest = {
                mostrarDevolucaoUsu = false
                livroSelecionadoParaAcao = null
            },
            title = { Text("Selecionar Usuário para Devolução") },
            text = {
                LazyColumn {
                    items(usuarios) { usuario ->
                        DropdownMenuItem(
                            text = { Text(usuario.nome) },
                            onClick = {
                                devolverLivro(livroSelecionadoParaAcao!!, usuario)
                                mostrarDevolucaoUsu = false
                                livroSelecionadoParaAcao = null
                            }
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarDevolucaoUsu = false
                        livroSelecionadoParaAcao = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
