package com.example.popularityquiz.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.popularityquiz.R
import com.example.popularityquiz.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {


    // learn hilt
    // provide view model with dependency using hilt

    private lateinit var model: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentScoreBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_score,
            container, false)

        val args: ScoreFragmentArgs by navArgs()
        viewModelFactory = ScoreViewModelFactory(args.score, args.gameOutcome, args.titleBackgroundImg)
        model = ViewModelProviders.of(this, viewModelFactory)[ScoreViewModel::class.java]

        model.finalScoreText.observe(viewLifecycleOwner) { newScore ->
            binding.finalScore.text = newScore.toString()
        }

        model.gameOutcomeText.observe(viewLifecycleOwner) { newOutcome ->
            binding.tvGameOutcome.text = newOutcome
        }

        model.titleBackgroundImg.observe(viewLifecycleOwner) {
            binding.lostImage.setBackgroundResource(it)
        }

        binding.playAgainBtn.setOnClickListener {
            model.onPlayAgain()
        }

        model.eventPlayAgain.observe(viewLifecycleOwner) { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToGameFragment())
                model.onPlayAgainComplete()
            }
        }

        return binding.root
    }
}