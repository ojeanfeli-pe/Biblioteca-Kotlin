package com.example.controlebiblioteca.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.controlebiblioteca.Livro

@Dao
interface LivroDao {
    @Insert
    suspend fun inserir(livro: Livro)

    @Update
    suspend fun atualizar(livro: Livro)

    @Delete
    suspend fun deletar(livro: Livro)

    @Query("SELECT * FROM livro")
    suspend fun obterTodos(): List<Livro>

    @Query("SELECT * FROM livro WHERE titulo LIKE :titulo OR autor LIKE :autor")
    suspend fun buscarLivro(titulo: String, autor: String): List<Livro>
}