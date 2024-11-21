package com.example.controlebiblioteca.Repositorio

import com.example.controlebiblioteca.DAO.LivroDao
import com.example.controlebiblioteca.classes.Livro

class LivroRepository(private val livroDao: LivroDao) {
    suspend fun adicionarLivro(livro: Livro) {
        livroDao.inserirLivro(livro)
    }

    suspend fun buscarLivros(): List<Livro> {
        return livroDao.listarLivros()
    }
}
