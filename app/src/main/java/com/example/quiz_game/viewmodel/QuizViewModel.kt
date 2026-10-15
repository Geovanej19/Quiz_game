package com.example.quiz_game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz_game.model.Question
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val playerName: String = "",
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val selectedIndex: Int? = null,   // null = ainda não respondeu
    val finished: Boolean = false
) {
    val current: Question get() = questions[currentIndex]
}

class QuizViewModel : ViewModel() {
    private val questions = listOf(
        Question(
            "Qual é a capital da França?",
            listOf("Londres", "Berlim", "Paris", "Madri"),
            2
        ),
        Question(
            "Qual planeta é conhecido como planeta vermelho?",
            listOf("Vênus", "Marte", "Júpiter", "Saturno"),
            1
        ),
        Question(
            "Quanto é 7 x 8?",
            listOf("54", "56", "64", "48"),
            1
        )
    )

    private val _state = MutableStateFlow(QuizUiState(questions = questions))
    val state: StateFlow<QuizUiState> = _state.asStateFlow()

    fun setName(name: String) = _state.update { it.copy(playerName = name) }

    fun answer(index: Int) {
        val s = _state.value
        if (s.selectedIndex != null) return  // bloqueia clique duplo
        val correct = index == s.current.correctIndex
        _state.update {
            it.copy(selectedIndex = index,
                score = if (correct) it.score + 1 else it.score
            )
        }

        viewModelScope.launch {
            delay(1200)  // Desafio 3: tempo para o usuário ver as cores
            _state.update {
                if (it.currentIndex + 1 >= it.questions.size) it.copy(finished = true)
                else it.copy(currentIndex = it.currentIndex + 1, selectedIndex = null)
            }
        }
    }

    fun restart() =
        _state.update {
            QuizUiState(
                playerName = it.playerName,
                questions = questions
            )
        }
}