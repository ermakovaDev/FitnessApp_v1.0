package com.example.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentDaysBinding


class DaysFragment : Fragment() {

    private lateinit var binding: FragmentDaysBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDaysBinding.inflate(inflater,container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment() //~ singleton
    }

}