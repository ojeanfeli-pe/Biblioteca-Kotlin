package com.example.controlebiblioteca.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emprestimo")
data class Emprestimo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dataEmprestimo: Long, //Para ser compatível com timestamps
    val dataDevolucao: Long?, //Pode ser nulo caso ainda não tenha sido devolvido
    val livroId: Long,
    val usuarioId: Int,
    val status: String
)