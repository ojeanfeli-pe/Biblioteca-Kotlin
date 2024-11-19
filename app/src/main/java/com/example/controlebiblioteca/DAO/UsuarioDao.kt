package com.example.controlebiblioteca.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.controlebiblioteca.classes.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun inserir(usuario: Usuario)

    @Update
    suspend fun atualizar(usuario: Usuario)

    @Delete
    suspend fun deletar(usuario: Usuario)

    @Query("SELECT * FROM usuario")
    suspend fun obterTodos(): List<Usuario>
}