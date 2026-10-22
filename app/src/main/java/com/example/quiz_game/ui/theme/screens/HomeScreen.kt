package com.example.quiz_game.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz_game.R
import com.example.quiz_game.viewmodel.QuizViewModel

// Tela inicial (Home) do aplicativo construída com Jetpack Compose
@Composable
fun HomeScreen(vm: QuizViewModel, onStart: () -> Unit) {
    // Observa o estado atual do ViewModel como um State do Compose reativo
    val state by vm.state.collectAsState()

    // Layout principal estruturado em coluna, centralizado e com fundo azul claro
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF62DBFB))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Exibição do logotipo do quiz carregado dos recursos
        Image(
            painter = painterResource(id = R.drawable.quiz_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(160.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Título principal exibido na tela inicial
        Text(text = "QUIZATRON 3000", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de texto para o usuário digitar o seu nome
        OutlinedTextField(
            value = state.playerName,
            onValueChange = { vm.setName(it) },
            label = { Text("Seu nome") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botão para iniciar o quiz, habilitado apenas se o nome não estiver em branco
        Button(
            onClick = onStart,
            enabled = state.playerName.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFDE2E),
                contentColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("COMEÇAR!")
        }
    }
}