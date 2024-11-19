package com.example.controlebiblioteca.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.controlebiblioteca.Emprestimo
import com.example.controlebiblioteca.Livro

@Dao
interface EmprestimoDao {

    @Insert
    suspend fun inserir(emprestimo: Emprestimo)

    @Update
    suspend fun atualizar(emprestimo: Livro)

    @Delete
    suspend fun deletar(emprestimo: Emprestimo)


    @Query("SELECT * FROM emprestimo WHERE livroId = :livroId AND status = 'EMPRESTADO'")
    suspend fun obterEmprestimosAtivosPorLivro(livroId: Int): List<Emprestimo>

    @Query("SELECT * FROM emprestimo WHERE usuarioId = :usuarioId")
    suspend fun obterEmprestimosPorUsuario(usuarioId: Int): List<Emprestimo>

    @Query("SELECT * FROM emprestimo")
    suspend fun obterTodosEmprestimos(): List<Emprestimo>

    @Query("SELECT * FROM livro WHERE id = :livroId")
    suspend fun getLivro(livroId: Int): Livro?

    @Update
    suspend fun atualizarLivro(livro: Livro)

}