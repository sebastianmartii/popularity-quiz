package com.example.popularityquiz.screens.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularityquiz.R

class GameViewModel : ViewModel() {

    data class AllAnime(val imageId: Int, val animeName: String, val animeMembersCount: Int)

    private val animeList = mutableListOf(
        AllAnime(R.drawable.opm2, "One Punch Man", 2_889_017),
        AllAnime(R.drawable.sao, "Sword Art Online", 2_820_520),
        AllAnime(R.drawable.dn, "Death Note", 3_514_766),
        AllAnime(R.drawable.fmab, "Full Metal Alchemist: Brotherhood", 2_987_556),
        AllAnime(R.drawable.mha, "My Hero Academia", 2_717_633),
        AllAnime(R.drawable.snk, "Attack on Titan", 3_534_611),
        AllAnime(R.drawable.kny, "Demon Slayer", 2_583_628),
        AllAnime(R.drawable.naruto, "Naruto", 2_578_145),
        AllAnime(R.drawable.tg, "Tokyo Ghoul", 2_574_379),
        AllAnime(R.drawable.hxh, "Hunter x Hunter", 2_489_725))

    private var currentTopAnime: AllAnime
    private var currentBottomAnime: AllAnime

    private val topBtnId = R.id.topBtn
    private val bottomBtnId = R.id.bottomBtn

    private var gameWon = false
    private var gameLost = false
    private var answerCorrect = false

    private val _eventGameFinish: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    private val _score: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val score: LiveData<Int>
        get() = _score

    private val _btnId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val btnId: LiveData<Int>
        get() = _btnId

    private val _animeTopImage: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val animeTopImage: LiveData<Int>
        get() = _animeTopImage

    private val _animeTopName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val animeTopName: LiveData<String>
        get() = _animeTopName

    private val _animeTopMembers: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val animeTopMembers: LiveData<Int>
        get() = _animeTopMembers

    private val _animeBottomImage: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val animeBottomImage: LiveData<Int>
        get() = _animeBottomImage

    private val _animeBottomName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val animeBottomName: LiveData<String>
        get() = _animeBottomName

    private val _animeBottomMembers: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val animeBottomMembers: LiveData<Int>
        get() = _animeBottomMembers

    private val _gameOutcome: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val gameOutcome: LiveData<String>
        get() = _gameOutcome

    private val _titleBackgroundImg: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val titleBackground: LiveData<Int>
        get() = _titleBackgroundImg

    private val _topMemVis: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val topMemVis: LiveData<Boolean>
        get() = _topMemVis

    private val _bottomMemVis: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val bottomMemVis: LiveData<Boolean>
        get() = _bottomMemVis

    init {
        _score.value = 0
        _gameOutcome.value = "Defeat"
        _topMemVis.value = false
        _bottomMemVis.value = false

        animeList.shuffle()
        currentTopAnime = animeList.removeAt(0)
        _animeTopMembers.value = currentTopAnime.animeMembersCount
        _animeTopName.value = currentTopAnime.animeName
        _animeTopImage.value = currentTopAnime.imageId

        animeList.shuffle()
        currentBottomAnime = animeList.removeAt(0)
        _animeBottomMembers.value = currentBottomAnime.animeMembersCount
        _animeBottomName.value = currentBottomAnime.animeName
        _animeBottomImage.value = currentBottomAnime.imageId

        _btnId.value = if (_animeBottomMembers.value!! >= _animeTopMembers.value!!) bottomBtnId else topBtnId
    }


    fun onAnimePicked() {
        if (answerCorrect) {
            if (_animeBottomMembers.value!! >= _animeTopMembers.value!!) {
                newTopAnime()
            } else {
                newBottomAnime()
            }
            _btnId.value = if (_animeBottomMembers.value!! >= _animeTopMembers.value!!) bottomBtnId else topBtnId
        } else {
            gameLost = true
        }
    }

    fun scoreUpdater() {
        if (answerCorrect) _score.value = _score.value!!.plus(1)
    }


    private fun newBottomAnime() {
        if (animeList.isEmpty()) {
            gameWon = true
            _gameOutcome.value = "Victory"
        } else {
            val newBottomAnime = animeList.removeAt(0)
            _animeBottomMembers.value = newBottomAnime.animeMembersCount
            _animeBottomName.value = newBottomAnime.animeName
            _animeBottomImage.value = newBottomAnime.imageId
            _topMemVis.value = true
            _bottomMemVis.value = false
        }
    }

    private fun newTopAnime() {
        if (animeList.isEmpty()) {
            gameWon = true
            _gameOutcome.value = "Victory"
        } else {
            val newTopAnime = animeList.removeAt(0)
            _animeTopMembers.value = newTopAnime.animeMembersCount
            _animeTopName.value = newTopAnime.animeName
            _animeTopImage.value = newTopAnime.imageId
            _topMemVis.value = false
            _bottomMemVis.value = true
        }
    }

    fun gameFinisher() {
        if (gameWon && !gameLost) {
            when (_btnId.value) {
                R.id.topBtn -> _titleBackgroundImg.value = _animeTopImage.value
                R.id.bottomBtn -> _titleBackgroundImg.value = _animeBottomImage.value
                else -> _titleBackgroundImg.value = null
            }
            _eventGameFinish.value = true
        } else if (!gameWon && gameLost) {
            _gameOutcome.value = "Defeat"
            when (_btnId.value) {
                R.id.topBtn -> _titleBackgroundImg.value = _animeBottomImage.value
                R.id.bottomBtn -> _titleBackgroundImg.value = _animeTopImage.value
                else -> _titleBackgroundImg.value = null
            }
            _eventGameFinish.value = true
        } else {
            _eventGameFinish.value = false
        }
    }

    fun onEventGameFinishCompleted() {
        _eventGameFinish.value = false
    }

    fun onAnswerCorrect() {
        answerCorrect = true
    }

    fun onAnswerIncorrect() {
        answerCorrect = false
    }
}