package com.example.controlebiblioteca.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "livro")
data class Livro(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    var disponivel: Boolean = true, //Valor padrão definido como true
    val categoria: String,
    var quantidadeTotal: Int
)