package com.example.controlebiblioteca

import BibliotecaScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controlebiblioteca.classes.Livro
import com.example.controlebiblioteca.screens.EmprestimoScreen
import com.example.controlebiblioteca.ui.theme.ControleBibliotecaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ControleBibliotecaTheme {
                // A navegação entre as telas
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "biblioteca") {
                    composable("biblioteca") {
                        BibliotecaScreen(
                            onLivroSelecionado = { livro ->
                                navController.navigate("emprestimo")
                            }
                        )
                    }
                    composable("emprestimo") {
                        EmprestimoScreen(
                            livro = Livro(1, "Livro Exemplo", "Autor Exemplo", 2020, true, "Ficção", 10),
                            onEmprestimoConcluido = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
