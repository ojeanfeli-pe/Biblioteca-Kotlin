package com.example.controlebiblioteca.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.controlebiblioteca.viewmodels.RelatorioViewModel

@Composable
fun RelatoriosScreen(
    relatorioViewModel: RelatorioViewModel = viewModel(),
    onVoltar: () -> Unit
) {
    val relatorios by relatorioViewModel.relatorios.observeAsState(emptyList())

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

        if (relatorios.isEmpty()) {
            Text(text = "Nenhum relatório disponível.", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(relatorios) { relatorio ->
                    RelatorioItem(
                        titulo = "${relatorio.acao}: ${relatorio.livroTitulo}",
                        detalhes = "Usuário: ${relatorio.usuarioNome}\nData: ${
                            java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(relatorio.dataHora)
                        }"
                    )
                }
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