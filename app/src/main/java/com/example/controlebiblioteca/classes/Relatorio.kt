package com.example.controlebiblioteca.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "relatorio")
data class Relatorio(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val acao: String, // "Empréstimo" ou "Devolução"
    val livroTitulo: String,
    val usuarioNome: String,
    val dataHora: Long // Timestamp do evento
)