package com.example.controlebiblioteca // Certifique-se de que este é o pacote correto
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controlebiblioteca.screens.AdicionarLivroScreen
import com.example.controlebiblioteca.screens.EmprestimoScreen
import com.example.controlebiblioteca.screens.HomeScreen
import com.example.controlebiblioteca.ui.theme.ControleBibliotecaTheme


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

    NavHost(navController = navController, startDestination = "adicionarLivro") {
        composable("adicionarLivro") {
            AdicionarLivroScreen(onLivroAdicionado = {
                // Navegar para a tela "home" após adicionar o livro
                navController.navigate("home") {
                    // Este código impede a navegação para a tela anterior quando você pressiona "voltar"
                    popUpTo("adicionarLivro") { inclusive = true }
                }
            })
        }
        composable("home") {
            // Apenas uma tela de exemplo para "home"
            Text("Tela Home")
        }
    }
}



