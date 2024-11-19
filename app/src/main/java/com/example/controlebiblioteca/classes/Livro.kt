package com.example.controlebiblioteca.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "livro")

data class Livro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    var disponivel: Boolean,
    val categoria: String,
    var quantidadeTotal: Int
)