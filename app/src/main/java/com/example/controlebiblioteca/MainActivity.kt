package com.example.controlebiblioteca

import BibliotecaScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controlebiblioteca.classes.Livro
import com.example.controlebiblioteca.screens.AdicionarLivroScreen
import com.example.controlebiblioteca.screens.EmprestimoScreen
import com.example.controlebiblioteca.screens.TelaPrincipal
import com.example.controlebiblioteca.ui.theme.ControleBibliotecaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ControleBibliotecaTheme {
                BibliotecaApp()
            }
        }
    }
}

@Composable
fun BibliotecaApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "tela_principal") {
        composable("tela_principal") {
            TelaPrincipal(
                onNavigateAdicionarLivro = { navController.navigate("adicionar_livro") },
                onNavigateEmprestar = { navController.navigate("biblioteca") },
            )
        }
        composable("adicionar_livro") {
            AdicionarLivroScreen(
                onLivroAdicionado = { navController.popBackStack() }
            )
        }
        composable("emprestimo") {
            val livro = Livro(
                id = 1,
                titulo = "Livro Exemplo",
                autor = "Autor Exemplo",
                anoPublicacao = 2020,
                disponivel = true,
                categoria = "Ficção",
                quantidadeTotal = 10
            )
            EmprestimoScreen(
                livro = livro,
                onEmprestimoConcluido = { navController.popBackStack() }
            )
        }
        composable("biblioteca") {
            BibliotecaScreen(
                onLivroSelecionado = { livro ->
                    navController.navigate("emprestimo")
                }
            )
        }
    }
}
