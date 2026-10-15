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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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
fun ResultScreen(vm: QuizViewModel, onRestart: () -> Unit) {
    val state by vm.state.collectAsState()

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(painterResource(
            R.drawable.quiz_logo),
            null,
            Modifier.padding(16.dp).size(64.dp)
        )
        Column(
            Modifier.fillMaxWidth().background(Color(0xFF62DBFB)).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = Color(0xFF72D8A8),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text("Bom trabalho, ${state.playerName}!", Modifier.padding(horizontal = 24.dp, vertical = 8.dp), fontSize = 20.sp)
            }

            Spacer(Modifier.height(24.dp))
            Text("Você acertou ${state.score} de ${state.questions.size} perguntas", fontSize = 20.sp)
        }

        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onRestart,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFDE2E), contentColor = Color.Black)
        ) { Text("JOGAR NOVAMENTE") }
    }
}