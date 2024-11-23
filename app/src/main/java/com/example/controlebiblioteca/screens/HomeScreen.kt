package com.example.controlebiblioteca.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateAdicionarUsuario: () -> Unit,
    onNavigateAdicionarLivro: () -> Unit,
    onNavigateEmprestar: () -> Unit,
    onNavigateRelatorios: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Bem-vindo ao Sistema de Biblioteca!",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onNavigateAdicionarUsuario,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cadastrar Usuarios")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateAdicionarLivro,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Gerenciar Livros")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateEmprestar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Gerenciar Empréstimos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateRelatorios,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Relatórios")
        }
    }
}