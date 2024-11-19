package com.example.controlebiblioteca.screens

import BibliotecaScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.controlebiblioteca.classes.Livro

@Composable
fun MainScreen(navController: NavHostController) {
    var livroSelecionado by remember { mutableStateOf<Livro?>(null) }

    BibliotecaScreen(
        onLivroSelecionado = { livro ->
            livroSelecionado = livro
            navController.navigate("emprestimo")
        }
    )

    if (livroSelecionado != null) {
        EmprestimoScreen(
            livro = livroSelecionado!!,
            onEmprestimoConcluido = { navController.popBackStack() }
        )
    }
}
