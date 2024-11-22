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

    // Carregar livros do banco de dados
    fun carregarLivros() {
        viewModelScope.launch {
            val listaLivros = withContext(Dispatchers.IO) {
                livroDao.listarLivros() // Esse método no DAO deve retornar uma lista de livros
            }
            _livros.value = listaLivros
        }
    }

    // Adicionar um novo livro
    fun adicionarLivro(livro: Livro) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                livroDao.inserirLivro(livro)
            }
            carregarLivros() // Atualiza a lista após a inserção
        }
    }

    // Editar um livro existente
    fun editarLivro(livro: Livro) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                livroDao.atualizarLivro(livro)
            }
            carregarLivros() // Atualiza a lista após a edição
        }
    }

    // Remover um livro
    fun removerLivro(livro: Livro) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                livroDao.deletarLivro(livro)
            }
            carregarLivros() // Atualiza a lista após a remoção
        }
    }

    // Emprestar um livro (marca o livro como não disponível)
    fun emprestarLivro(livro: Livro) {
        if (livro.disponivel) {
            val livroAtualizado = livro.copy(disponivel = false)
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    livroDao.atualizarLivro(livroAtualizado)
                }
                carregarLivros() // Atualiza a lista após a modificação
            }
        }
    }

    // Devolver um livro (marca o livro como disponível)
    fun devolverLivro(livro: Livro) {
        val livroAtualizado = livro.copy(disponivel = true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                livroDao.atualizarLivro(livroAtualizado)
            }
            carregarLivros() // Atualiza a lista após a modificação
        }
    }

    // Buscar livros por título ou autor
    fun buscarLivros(query: String): LiveData<List<Livro>> {
        val resultados = MutableLiveData<List<Livro>>()
        viewModelScope.launch {
            val listaFiltrada = withContext(Dispatchers.IO) {
                livroDao.buscarLivros("%$query%") // Buscar no banco de dados
            }
            resultados.postValue(listaFiltrada) // Atualiza os resultados da busca
        }
        return resultados
    }
}