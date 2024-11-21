import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlebiblioteca.BibliotecaDatabase
import com.example.controlebiblioteca.classes.Livro
import kotlinx.coroutines.launch

class LivroViewModel(application: Application) : AndroidViewModel(application) {
    private val livroDao = BibliotecaDatabase.getDatabase(application).livroDao()
    private val _livros = MutableLiveData<List<Livro>>() // LiveData para observar a lista de livros
    val livros: LiveData<List<Livro>> get() = _livros

    init {
        carregarLivros() // Carrega a lista de livros ao inicializar
    }

    fun carregarLivros() {
        viewModelScope.launch {
            _livros.value = livroDao.listarLivros() // Atualiza o LiveData com os livros do banco
        }
    }

    fun adicionarLivro(livro: Livro) {
        viewModelScope.launch {
            livroDao.inserirLivro(livro) // Insere o livro no banco de dados
            carregarLivros() // Atualiza a lista após adicionar
        }
    }
}
