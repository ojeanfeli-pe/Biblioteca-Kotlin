package com.example.controlebiblioteca

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {
    private val database = BibliotecaDatabase.getDatabase(application)

    fun adicionarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            database.usuarioDao().inserir(usuario)
        }
    }
}
