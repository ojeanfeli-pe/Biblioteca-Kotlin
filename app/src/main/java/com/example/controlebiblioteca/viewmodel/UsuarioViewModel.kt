import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlebiblioteca.classes.Usuario
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    var usuarios = mutableStateListOf<Usuario>() // Lista de livros (inicialmente vazia)

    init {
        viewModelScope.launch {
            usuarios.add(Usuario(1,"andre","andre@gmail.com", "4199999999", "usuario", "19/11/2024"))
            usuarios.add(Usuario(1,"yuri","yuri@gmail.com", "4199999998", "usuario", "19/11/2024"))
            usuarios.add(Usuario(1,"joao","joao@gmail.com", "4199999997", "usuario", "19/11/2024"))
        }
    }
}
