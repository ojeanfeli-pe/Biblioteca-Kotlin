package com.example.controlebiblioteca

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emprestimo")
data class Emprestimo (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dataEmprestimo: String,
    val dataDevolucao: String,
    val livroId: Int,
    val usuarioId: Int,
    val status: String
)