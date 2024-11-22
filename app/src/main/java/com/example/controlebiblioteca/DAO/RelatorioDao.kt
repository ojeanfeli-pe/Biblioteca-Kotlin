package com.example.controlebiblioteca.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.controlebiblioteca.classes.Relatorio

@Dao
interface RelatorioDao {
    @Insert
    suspend fun insert(relatorio: Relatorio)

    @Query("SELECT * FROM relatorio ORDER BY dataHora DESC")
    fun getAllRelatorios(): LiveData<List<Relatorio>>
}
