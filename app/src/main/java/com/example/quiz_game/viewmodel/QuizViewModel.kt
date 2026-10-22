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

// Representa o estado da interface do usuário do quiz (UI State)
data class QuizUiState(
    val playerName: String = "",
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val selectedIndex: Int? = null,   // null = ainda não respondeu
    val finished: Boolean = false
) {
    // Propriedade calculada que retorna a pergunta atual com base no índice
    val current: Question get() = questions[currentIndex]
}

// ViewModel responsável por gerenciar a lógica de negócios e o estado do quiz
class QuizViewModel : ViewModel() {
    // Lista fixa de perguntas predefinidas do jogo
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

    // Estado mutável interno exposto através de um StateFlow imutável para a UI
    private val _state = MutableStateFlow(QuizUiState(questions = questions))
    val state: StateFlow<QuizUiState> = _state.asStateFlow()

    // Atualiza o nome do jogador no estado
    fun setName(name: String) = _state.update { it.copy(playerName = name) }

    // Processa a resposta escolhida pelo jogador
    fun answer(index: Int) {
        val s = _state.value
        if (s.selectedIndex != null) return  // bloqueia clique duplo enquanto exibe o resultado da resposta
        val correct = index == s.current.correctIndex

        // Atualiza o índice selecionado e incrementa a pontuação se a resposta estiver correta
        _state.update {
            it.copy(selectedIndex = index,
                score = if (correct) it.score + 1 else it.score
            )
        }

        // Inicia uma corotina para aguardar um tempo antes de avançar para a próxima pergunta ou finalizar
        viewModelScope.launch {
            delay(1200)  // Desafio 3: tempo para o usuário ver as cores de certo/errado
            _state.update {
                if (it.currentIndex + 1 >= it.questions.size) it.copy(finished = true)
                else it.copy(currentIndex = it.currentIndex + 1, selectedIndex = null)
            }
        }
    }

    // Reinicia o quiz mantendo o nome do jogador digitado anteriormente
    fun restart() =
        _state.update {
            QuizUiState(
                playerName = it.playerName,
                questions = questions
            )
        }
}