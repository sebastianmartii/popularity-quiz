package com.example.popularityquiz.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ScoreViewModel(finalScore: Int, gameOutcome: String, titleBackground: Int) : ViewModel() {

    private val _eventPlayAgain: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    private val _finalScoreText: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val finalScoreText: LiveData<Int>
        get() = _finalScoreText

    private val _gameOutcomeText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val gameOutcomeText: LiveData<String>
        get() = _gameOutcomeText

    private val _titleBackgroundImg: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val titleBackgroundImg: LiveData<Int>
        get() = _titleBackgroundImg

    init {
        _titleBackgroundImg.value = titleBackground
        _gameOutcomeText.value = gameOutcome
        _finalScoreText.value = finalScore
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

}