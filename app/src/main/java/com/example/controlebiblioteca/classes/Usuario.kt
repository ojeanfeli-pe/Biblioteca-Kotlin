package com.example.controlebiblioteca.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val email: String,
    val telefone: String?, //Campo opcional
    val tipoUsuario: String, //Ex: "funcionario" ou "usuario"
    val dataCadastro: Long // Long para representar timestamps
)