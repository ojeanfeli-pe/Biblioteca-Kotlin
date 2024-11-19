package com.example.controlebiblioteca

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.controlebiblioteca.DAO.EmprestimoDao
import com.example.controlebiblioteca.DAO.LivroDao
import com.example.controlebiblioteca.DAO.UsuarioDao
import com.example.controlebiblioteca.classes.Emprestimo
import com.example.controlebiblioteca.classes.Livro
import com.example.controlebiblioteca.classes.Usuario

@Database(entities = [Livro::class, Usuario::class, Emprestimo::class], version = 1)
abstract class BibliotecaDatabase : RoomDatabase() {

    abstract fun livroDao(): LivroDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun emprestimoDao(): EmprestimoDao

    companion object{
        @Volatile
        private var INSTANCE: BibliotecaDatabase? = null

        fun getDatabase(context: Context): BibliotecaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BibliotecaDatabase::class.java,
                    "biblioteca_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}