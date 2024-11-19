import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlebiblioteca.BibliotecaDatabase
import com.example.controlebiblioteca.classes.Emprestimo
import com.example.controlebiblioteca.classes.Livro
import com.example.controlebiblioteca.classes.Usuario
import kotlinx.coroutines.launch

class EmprestimoViewModel(application: Application) : AndroidViewModel(application) {
    private val livroDao = BibliotecaDatabase.getDatabase(application).livroDao()
    private val usuarioDao = BibliotecaDatabase.getDatabase(application).usuarioDao()
    private val emprestimoDao = BibliotecaDatabase.getDatabase(application).emprestimoDao()

    val livros = mutableStateListOf<Livro>()
    val usuarios = mutableStateListOf<Usuario>()

    init {
        viewModelScope.launch {
            livros.addAll(livroDao.obterLivros())
            usuarios.addAll(usuarioDao.obterTodos())
        }
    }

    fun emprestarLivro(livro: Livro, usuario: Usuario, dataEmprestimo: String, dataDevolucao: String) {
        if (livro.disponivel) {
            val emprestimo = Emprestimo(
                dataEmprestimo = dataEmprestimo,
                dataDevolucao = dataDevolucao,
                livroId = livro.id,
                usuarioId = usuario.id,
                status = "EMPRESTADO"
            )
            viewModelScope.launch {
                emprestimoDao.inserir(emprestimo)
                livro.disponivel = false // Marca o livro como não disponível
                livroDao.inserirLivro(livro)
            }
        } else {
            // Exibe mensagem de erro
            println("Livro não disponível para empréstimo.")
        }
    }
}
