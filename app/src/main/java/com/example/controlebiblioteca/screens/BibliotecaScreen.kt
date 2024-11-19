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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibliotecaScreen(
    livroViewModel: LivroViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel(),
    onLivroSelecionado: (Livro) -> Unit
) {
    val livros = livroViewModel.livros

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Bem-vindo à Biblioteca!")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Livros Disponíveis para Empréstimo")

        LazyColumn {
            items(livros) { livro ->
                LivroItem(livro = livro, onClick = { onLivroSelecionado(livro) })
            }
        }
    }
}

@Composable
fun LivroItem(livro: Livro, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Título: ${livro.titulo}")
        Text(text = "Autor: ${livro.autor}")
        Text(text = "Ano: ${livro.anoPublicacao}")

        val disponibilidade = if (livro.disponivel) "Disponível" else "Emprestado"
        Text(text = "Status: $disponibilidade, ${livro.quantidadeTotal}", color = if (livro.disponivel) Color.Green else Color.Red)


        Spacer(modifier = Modifier.height(8.dp))

        if (livro.disponivel) {
            Button(onClick = onClick) {
                Text(text = "Selecionar para Emprestar")
            }
        } else {
            Text(text = "Livro não disponível", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

