package com.example.controlebiblioteca.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.controlebiblioteca.classes.Emprestimo
import com.example.controlebiblioteca.classes.Livro

@Dao
interface LivroDao {

    @Insert
    suspend fun inserirLivro(livro: Livro)

    @Update
    suspend fun atualizarLivro(livro: Livro)

    @Query("SELECT * FROM livro")
    suspend fun obterLivros(): List<Livro>

    @Query("SELECT * FROM livro WHERE id = :id")
    suspend fun obterLivroPorId(id: Int): Livro?
}
