import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
    val mensagem = mutableStateOf("")

    init {
        viewModelScope.launch {
            livros.addAll(livroDao.listarLivros())
            usuarios.addAll(usuarioDao.listarUsuarios())
        }
    }

    fun emprestarLivro(livro: Livro, usuario: Usuario, dataEmprestimo: Long, dataDevolucao: Long) {
        if (livro.disponivel) {
            val emprestimo = Emprestimo(
                dataEmprestimo = dataEmprestimo,
                dataDevolucao = dataDevolucao,
                livroId = livro.id,
                usuarioId = usuario.id,
                status = "EMPRESTADO"
            )
            viewModelScope.launch {
                // Inserir o empréstimo no banco de dados
                emprestimoDao.inserir(emprestimo)

                // Atualizar a disponibilidade do livro
                livro.disponivel = false
                livroDao.atualizarLivro(livro)  // Atualiza o livro (ao invés de inserir novamente)

                // Mensagem de sucesso
                mensagem.value = "Empréstimo realizado com sucesso para ${usuario.nome}!"
            }
        } else {
            // Exibe mensagem de erro
            mensagem.value = "Livro não disponível para empréstimo."
        }
    }
}