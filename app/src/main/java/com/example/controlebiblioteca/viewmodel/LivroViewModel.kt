import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlebiblioteca.BibliotecaDatabase
import com.example.controlebiblioteca.classes.Livro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LivroViewModel(application: Application) : AndroidViewModel(application) {

    private val livroDao = BibliotecaDatabase.getDatabase(application).livroDao()
    private val _livros = MutableLiveData<List<Livro>>() // LiveData para observar a lista de livros
    val livros: LiveData<List<Livro>> get() = _livros

    init {
        carregarLivros() // Carrega a lista de livros ao inicializar
    }

    fun carregarLivros() {
        viewModelScope.launch {

            val listaLivros = withContext(Dispatchers.IO){// Atualiza o LiveData com os livros do banco
                livroDao.listarLivros()
            }
            // Atualização da LiveData na thread principal
            _livros.value = listaLivros
        }
    }

    fun adicionarLivro(livro: Livro) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                livroDao.inserirLivro(livro) // Insere o livro no banco de dados
                carregarLivros() // Recarrega a lista após a inserção
            }
        }


    }
}
