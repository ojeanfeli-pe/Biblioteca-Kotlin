package com.example.controlebiblioteca

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liver")

data class Livro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    val disponivel: Boolean,
    val categoria: String,
    val quantidadeTotal: Int
)