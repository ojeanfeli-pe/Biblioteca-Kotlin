import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlebiblioteca.classes.Livro
import kotlinx.coroutines.launch

class LivroViewModel : ViewModel() {
    var livros = mutableStateListOf<Livro>()

    init {
        // Adicionando livros de exemplo
        livros.add(Livro(1, "Livro Exemplo", "Autor Exemplo", 2020, true, "Ficção", 10))
        livros.add(Livro(2, "Outro Livro", "Outro Autor", 2018, true, "Aventura", 5))
    }

    // Função para emprestar um livro
    fun emprestarLivro(livro: Livro) {
        val livroIndex = livros.indexOfFirst { it.id == livro.id }
        if (livroIndex != -1 && livros[livroIndex].quantidadeTotal > 0) {
            // Atualiza a quantidade e a disponibilidade
            val livroEmprestado = livros[livroIndex].copy(
                quantidadeTotal = livros[livroIndex].quantidadeTotal - 1,
                disponivel = livros[livroIndex].quantidadeTotal - 1 > 0
            )
        }
    }
}





