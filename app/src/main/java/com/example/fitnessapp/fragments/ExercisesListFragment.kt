package com.example.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.R
import com.example.fitnessapp.adapters.DayModel
import com.example.fitnessapp.adapters.DaysAdapter
import com.example.fitnessapp.adapters.ExerciseAdapter
import com.example.fitnessapp.databinding.FragmentDaysBinding
import com.example.fitnessapp.databinding.FragmentExercisesListBinding
import com.example.fitnessapp.utilites.MainViewModel


class  ExercisesListFragment : Fragment() {

    private lateinit var binding: FragmentExercisesListBinding
    private val model: MainViewModel by activityViewModels()
    private lateinit var adapter: ExerciseAdapter
    private var actionBarMod : ActionBar? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExercisesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRCView()
        model.mutableListExercise.observe(viewLifecycleOwner) {
            adapter.submitList(it)

        }
    }

    private fun initRCView() = with(binding) {
        actionBarMod = (activity as AppCompatActivity).supportActionBar
        actionBarMod?.title = getString(R.string.day_exercise_list)
        adapter = ExerciseAdapter()
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter

        btnExerciseStart.setOnClickListener {
            com.example.fitnessapp.utilites.FragmentManager.setFragment(
                WaitingFragment.newInstance(),
                activity as AppCompatActivity
            )
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ExercisesListFragment() //~ singleton
    }

}