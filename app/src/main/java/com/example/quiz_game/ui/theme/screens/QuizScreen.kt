package com.example.quiz_game.ui.theme.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.quiz_game.R

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz_game.viewmodel.QuizViewModel

@Composable
fun QuizScreen(vm: QuizViewModel, onFinish: () -> Unit) {
    val state by vm.state.collectAsState()

    LaunchedEffect(state.finished) { if (state.finished) onFinish() }
    if (state.finished) return

    Column(
        Modifier.fillMaxSize().background(Color(0xFFFF92D3)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(
                R.drawable.quiz_logo),
            null,
            Modifier.size(64.dp)
        )

        Spacer(Modifier.height(16.dp))
        Surface(
            color = Color(0xFF72D8A8),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text("Pergunta ${state.currentIndex + 1} de ${state.questions.size}",
                Modifier.padding(horizontal = 32.dp, vertical = 8.dp), fontSize = 20.sp)
        }

        Spacer(Modifier.height(16.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(Modifier.padding(16.dp)) {
                Text(state.current.text, fontSize = 20.sp)
                Spacer(Modifier.height(16.dp))
                state.current.options.forEachIndexed { i, option ->
                    val bg = when {
                        state.selectedIndex == null -> Color.White
                        i == state.current.correctIndex -> Color(0xFF4CAF50) // verde: correta
                        i == state.selectedIndex -> Color(0xFFE53935)        // vermelho: errada escolhida
                        else -> Color.White
                    }

                    OutlinedButton(
                        onClick = { vm.answer(i) },
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = bg, contentColor = Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) { Text(option, Modifier.fillMaxWidth()) }
                }
            }
        }
    }
}