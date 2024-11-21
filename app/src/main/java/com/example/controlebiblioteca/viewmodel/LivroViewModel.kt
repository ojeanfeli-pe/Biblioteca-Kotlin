import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlebiblioteca.classes.Livro
import kotlinx.coroutines.launch
import com.example.controlebiblioteca.Repositorio.LivroRepository


class LivroViewModel(private val repository: LivroRepository, livro: Livro) : ViewModel() {
    var livros = mutableStateListOf<Livro>()

    init {
        carregarLivros()
    }

    fun adicionarLivro(livro: Livro) {
        viewModelScope.launch {
            repository.adicionarLivro(livro)
            carregarLivros() // Atualiza a lista de livros após adicionar
        }
    }

    private fun carregarLivros() {
        viewModelScope.launch {
            livros.clear()
            livros.addAll(repository.buscarLivros())
        }
    }
}