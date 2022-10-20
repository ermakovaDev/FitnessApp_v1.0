package com.example.fitnessapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnessapp.adapters.ExerciseAdapter
import com.example.fitnessapp.databinding.FragmentDayFinishBinding
import com.example.fitnessapp.databinding.FragmentExercisesListBinding
import com.example.fitnessapp.databinding.FragmentWaitingBinding
import com.example.fitnessapp.utilites.FragmentManager
import com.example.fitnessapp.utilites.TimeUtils
import pl.droidsonroids.gif.GifDrawable


class DayFinishFragment : Fragment() {

    private lateinit var binding: FragmentDayFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDayFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivFinishImage.setImageDrawable(
            GifDrawable(
                (activity as AppCompatActivity).assets,
                "finish.gif"
            )
        )
        binding.btnFinishButton.setOnClickListener {
            FragmentManager.setFragment(DaysFragment.newInstance(), activity as AppCompatActivity)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = DayFinishFragment() //~ singleton
    }
}