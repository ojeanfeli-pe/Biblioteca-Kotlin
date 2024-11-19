package com.example.controlebiblioteca

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider

class BibliotecaScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibliotecaScreenContent()
        }
    }

    @Composable
    fun BibliotecaScreenContent() {
        val livroViewModel = ViewModelProvider(this).get(LivroViewModel::class.java)
        val usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)
        val emprestimoViewModel = ViewModelProvider(this).get(EmprestimoViewModel::class.java)

        // Variáveis para controle de input
        var livroTitulo by remember { mutableStateOf("") }
        var livroAutor by remember { mutableStateOf("") }
        var livroAno by remember { mutableStateOf("") }
        var livroCategoria by remember { mutableStateOf("") }
        var livroQuantidade by remember { mutableStateOf("") }

        var usuarioNome by remember { mutableStateOf("") }
        var usuarioEmail by remember { mutableStateOf("") }

        var emprestimoLivroId by remember { mutableStateOf("") }
        var emprestimoUsuarioId by remember { mutableStateOf("") }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Adicionar Livro")
            TextField(value = livroTitulo, onValueChange = { livroTitulo = it }, label = { Text("Título") })
            TextField(value = livroAutor, onValueChange = { livroAutor = it }, label = { Text("Autor") })
            TextField(value = livroAno, onValueChange = { livroAno = it }, label = { Text("Ano de Publicação") })
            TextField(value = livroCategoria, onValueChange = { livroCategoria = it }, label = { Text("Categoria") })
            TextField(value = livroQuantidade, onValueChange = { livroQuantidade = it }, label = { Text("Quantidade Total") })

            Button(onClick = {
                if (livroTitulo.isNotBlank() && livroAutor.isNotBlank() && livroAno.isNotBlank() && livroQuantidade.isNotBlank()) {
                    val livro = Livro(
                        titulo = livroTitulo,
                        autor = livroAutor,
                        anoPublicacao = livroAno.toInt(),
                        categoria = livroCategoria,
                        quantidadeTotal = livroQuantidade.toInt(),
                        disponivel = true
                    )
                    livroViewModel.adicionarLivro(livro)
                    Toast.makeText(this@BibliotecaScreen, "Livro Adicionado!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@BibliotecaScreen, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Salvar Livro")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Adicionar Usuário")
            TextField(value = usuarioNome, onValueChange = { usuarioNome = it }, label = { Text("Nome") })
            TextField(value = usuarioEmail, onValueChange = { usuarioEmail = it }, label = { Text("Email") })

            Button(onClick = {
                if (usuarioNome.isNotBlank() && usuarioEmail.isNotBlank()) {
                    val usuario = Usuario(
                        nome = usuarioNome,
                        email = usuarioEmail,
                        telefone = null,
                        tipoUsuario = "funcionario", // Ajuste conforme necessário
                        dataCadastro = "2024-11-01"
                    )
                    usuarioViewModel.adicionarUsuario(usuario)
                    Toast.makeText(this@BibliotecaScreen, "Usuário Adicionado!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@BibliotecaScreen, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Salvar Usuário")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Registrar Empréstimo")
            TextField(value = emprestimoLivroId, onValueChange = { emprestimoLivroId = it }, label = { Text("Livro ID") })
            TextField(value = emprestimoUsuarioId, onValueChange = { emprestimoUsuarioId = it }, label = { Text("Usuário ID") })

            Button(onClick = {
                if (emprestimoLivroId.isNotBlank() && emprestimoUsuarioId.isNotBlank()) {
                    val emprestimo = Emprestimo(
                        dataEmprestimo = "2024-11-01",
                        dataDevolucao = "2024-11-10",  // Ajuste conforme necessário
                        livroId = emprestimoLivroId.toInt(),
                        usuarioId = emprestimoUsuarioId.toInt(),
                        status = "EMPRESTADO"
                    )
                    emprestimoViewModel.adicionarEmprestimo(emprestimo)
                    Toast.makeText(this@BibliotecaScreen, "Empréstimo Registrado!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@BibliotecaScreen, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Salvar Empréstimo")
            }
        }
    }
}
