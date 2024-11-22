import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.controlebiblioteca.classes.Livro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibliotecaScreen(
    livroViewModel: LivroViewModel = viewModel(),
    onLivroSelecionado: (Livro) -> Unit
) {
    val livros by livroViewModel.livros.observeAsState(initial = emptyList()) // Usando fluxo de dados

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Bem-vindo à Biblioteca!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Livros Disponíveis para Empréstimo", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(livros) { livro ->
                LivroItem(livro = livro, onClick = { onLivroSelecionado(livro) })
                Divider()
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
        Text(
            text = "Status: $disponibilidade (${livro.quantidadeTotal})",
            color = if (livro.disponivel) Color.Green else Color.Red
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onClick,
            enabled = livro.disponivel
        ) {
            Text(text = if (livro.disponivel) "Selecionar para Empréstimo" else "Indisponível")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BibliotecaScreen(
//    livroViewModel: LivroViewModel = viewModel(),
//    usuarioViewModel: UsuarioViewModel = viewModel(),
//    onLivroSelecionado: (Livro) -> Unit
//) {
//    val livros = livroViewModel.livros
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = "Bem-vindo à Biblioteca!")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(text = "Livros Disponíveis para Empréstimo")
//
////        LazyColumn {
////            items(livros) { livro ->
////                LivroItem(livro = livro, onClick = { onLivroSelecionado(livro) })
////            }
////        }
//    }
//}
//
//@Composable
//fun LivroItem(livro: Livro, onClick: () -> Unit) {
//    Column(modifier = Modifier.padding(8.dp)) {
//        Text(text = "Título: ${livro.titulo}")
//        Text(text = "Autor: ${livro.autor}")
//        Text(text = "Ano: ${livro.anoPublicacao}")
//        val disponibilidade = if (livro.disponivel) "Disponível" else "Emprestado"
//        Text(text = "Status: $disponibilidade, ${livro.quantidadeTotal}", color = if (livro.disponivel) Color.Green else Color.Red)
//
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        if (livro.disponivel) {
//            Button(onClick = onClick) {
//                Text(text = "Selecionar para Emprestar")
//            }
//        } else {
//            Text(text = "Livro não disponível", color = Color.Red)
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//    }
//}

