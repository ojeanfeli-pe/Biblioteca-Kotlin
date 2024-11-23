package com.example.controlebiblioteca.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.controlebiblioteca.BibliotecaDatabase
import com.example.controlebiblioteca.classes.Relatorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RelatorioViewModel(application: Application) : AndroidViewModel(application) {
    private val relatorioDao = BibliotecaDatabase.getDatabase(application).relatorioDao()

    val relatorios: LiveData<List<Relatorio>> = relatorioDao.getAllRelatorios()

    fun adicionarRelatorio(relatorio: Relatorio) {
        viewModelScope.launch(Dispatchers.IO) {
            relatorioDao.insert(relatorio)
        }
    }

}
