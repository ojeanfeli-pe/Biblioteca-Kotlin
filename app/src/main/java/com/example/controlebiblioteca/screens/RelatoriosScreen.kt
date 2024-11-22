package com.example.controlebiblioteca.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RelatoriosScreen(onVoltar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Relatórios da Biblioteca",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(1) { // Simulação de relatórios; pode ser ajustado conforme os dados disponíveis
                RelatorioItem(
                    titulo = "Livros Mais Emprestados",
                    detalhes = "1. O Senhor dos Anéis (10 vezes)\n2. Dom Quixote (8 vezes)"
                )
                RelatorioItem(
                    titulo = "Total de Empréstimos",
                    detalhes = "45 empréstimos realizados este mês."
                )
                RelatorioItem(
                    titulo = "Usuários Ativos",
                    detalhes = "20 usuários realizaram empréstimos."
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onVoltar, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Voltar")
        }
    }
}

@Composable
fun RelatorioItem(titulo: String, detalhes: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = titulo, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = detalhes, style = MaterialTheme.typography.bodyMedium)
        }
    }
}