package com.example.popularityquiz.screens.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.popularityquiz.R
import com.example.popularityquiz.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {

    private lateinit var binding: FragmentRulesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rules, container, false)

        binding.getStartedBtn.setOnClickListener {
            findNavController().navigate(RulesFragmentDirections.actionRulesFragmentToGameFragment())
        }

        return binding.root
    }
}