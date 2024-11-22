package com.example.controlebiblioteca
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controlebiblioteca.screens.EmprestimoScreen
import com.example.controlebiblioteca.screens.GerenciamentoDeLivrosScreen
import com.example.controlebiblioteca.screens.HomeScreen
import com.example.controlebiblioteca.screens.RelatoriosScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateAdicionarLivro = { navController.navigate("adicionarLivro") },
                onNavigateEmprestar = { navController.navigate("emprestimos") },
                onNavigateRelatorios = { navController.navigate("relatorios") }
            )
        }
        composable("adicionarLivro") {
            GerenciamentoDeLivrosScreen(onVoltar = {
                navController.navigate("home") {
                    popUpTo("adicionarLivro") { inclusive = true }
                }
            })
        }
        composable("emprestimos") {
            EmprestimoScreen(onVoltar = { navController.navigate("home") })
        }
        composable("relatorios") {
            RelatoriosScreen(onVoltar = { navController.navigate("home") })
        }
    }
}

