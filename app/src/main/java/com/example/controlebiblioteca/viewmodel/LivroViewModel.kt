package com.example.controlebiblioteca

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LivroViewModel(application: Application) : AndroidViewModel(application) {
    private val database = BibliotecaDatabase.getDatabase(application)

    fun adicionarLivro(livro: Livro) {
        viewModelScope.launch {
            database.livroDao().inserir(livro)
        }
    }
}
