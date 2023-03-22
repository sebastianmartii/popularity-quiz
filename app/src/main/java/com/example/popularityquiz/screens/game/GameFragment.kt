package com.example.popularityquiz.screens.game

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.popularityquiz.R
import com.example.popularityquiz.databinding.FragmentGameBinding



class GameFragment : Fragment() {

    private val model: GameViewModel by viewModels()
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        binding.gameViewModel = model
        binding.lifecycleOwner = this

        model.eventGameFinish.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished) {
                gameFinish()
                model.onEventGameFinishCompleted()
            }
        }

        binding.topBtn.setOnClickListener {
            answerPicked(it)
        }

        binding.bottomBtn.setOnClickListener {
            answerPicked(it)
        }

        return binding.root
    }

    private fun gameFinish() {
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(model.gameOutcome.value ?: "Defeat"
            ,model.titleBackground.value ?: R.drawable.fmab ,model.score.value ?: 0)
        findNavController().navigate(action)
    }

    private fun answerPicked(view: View) {
        binding.bottomMembers.isVisible = true
        binding.topMembers.isVisible = true
        binding.bottomBtn.isVisible = false
        binding.topBtn.isVisible = false
        if (view.id == model.btnId.value) model.onAnswerCorrect() else model.onAnswerIncorrect()
        model.scoreUpdater()
        Handler(Looper.getMainLooper()).postDelayed({
            model.onAnimePicked()
            model.gameFinisher()
            model.bottomMemVis.observe(viewLifecycleOwner) {
                binding.bottomMembers.isVisible = it
            }
            model.topMemVis.observe(viewLifecycleOwner) {
                binding.topMembers.isVisible = it
            }
            binding.bottomBtn.isVisible = true
            binding.topBtn.isVisible = true
        }, 1000)
    }
}