import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
            BibliotecaApp()
        }
    }
}

@Composable
fun BibliotecaApp() {
    val navController = rememberNavController() // Cria o NavController
    NavHost(navController = navController, startDestination = "tela_principal") {
        // Tela Principal
        composable("tela_principal") {
            HomeScreen(
                onNavigateAdicionarLivro = { navController.navigate("adicionar_livro") },
                onNavigateEmprestar = { navController.navigate("emprestimo") },
            )
        }

        // Tela de Adicionar Livro
        composable("adicionar_livro") {
            AdicionarLivroScreen(
                onLivroAdicionado = { navController.popBackStack() } // Volta para a tela anterior
            )
        }

        // Tela de Empréstimo
        // Tela de Empréstimo
        composable("emprestimo/{livro}") { backStackEntry ->
            val livro = backStackEntry.arguments?.getString("livro") // Obtendo o parâmetro
            EmprestimoScreen(
                livro = livro,
                navController = navController, // Passando o NavController corretamente
                onEmprestimoConcluido = {
                    navController.popBackStack()
                }
            )
        }
    }}

