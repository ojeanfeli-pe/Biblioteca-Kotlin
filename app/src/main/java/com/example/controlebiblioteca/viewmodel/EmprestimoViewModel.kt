package com.example.controlebiblioteca

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EmprestimoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = BibliotecaDatabase.getDatabase(application)

    fun adicionarEmprestimo(emprestimo: Emprestimo) {
        viewModelScope.launch {
            database.emprestimoDao().inserir(emprestimo)
        }
    }
}
