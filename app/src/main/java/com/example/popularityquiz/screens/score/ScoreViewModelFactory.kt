package com.example.popularityquiz.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScoreViewModelFactory(
    private val finalScore: Int,
    private val gameOutcome: String,
    private val titleBackground: Int
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore, gameOutcome, titleBackground) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}