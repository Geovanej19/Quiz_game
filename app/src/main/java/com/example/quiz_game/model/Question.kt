
package com.example.quiz_game.model

data class Question(
    val text: String,
    val options: List<String>,
    val correctIndex: Int
)
