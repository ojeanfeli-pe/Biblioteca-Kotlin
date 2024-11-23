import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.controlebiblioteca.BibliotecaDatabase
import com.example.controlebiblioteca.classes.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {
    private val usuarioDao = BibliotecaDatabase.getDatabase(application).usuarioDao()
    private val _usuarios = MutableLiveData<List<Usuario>>() // LiveData para observar a lista de livros
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    init {
        carregarUsuarios()
        }

        fun carregarUsuarios() {
            viewModelScope.launch {
                val listaUsuarios = withContext(Dispatchers.IO) {
                    usuarioDao.listarUsuarios() // Esse método no DAO deve retornar uma lista de livros
                }
                _usuarios.value = listaUsuarios
            }
        }

        // Adicionar um novo livro
        fun adicionarUsuario(usuario: Usuario) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    usuarioDao.inserir(usuario)
                }
                carregarUsuarios() // Atualiza a lista após a inserção
            }
        }
    // Remover um livro
    fun removerUsuario(usuario: Usuario) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                usuarioDao.deletar(usuario)
            }
            carregarUsuarios() // Atualiza a lista após a remoção
        }
    }
}

//class UsuarioViewModel : ViewModel() {
//    var usuarios = mutableStateListOf<Usuario>() // Lista de livros (inicialmente vazia)
//
//    init {
//        viewModelScope.launch {
//            usuarios.add(Usuario(1,"andre","andre@gmail.com", "4199999999", "usuario", "19/11/2024"))
//            usuarios.add(Usuario(1,"yuri","yuri@gmail.com", "4199999998", "usuario", "19/11/2024"))
//            usuarios.add(Usuario(1,"joao","joao@gmail.com", "4199999997", "usuario", "19/11/2024"))
//        }
//    }
//}
