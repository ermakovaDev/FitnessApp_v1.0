package com.example.fitnessapp.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.R
import com.example.fitnessapp.adapters.DayModel
import com.example.fitnessapp.adapters.DaysAdapter
import com.example.fitnessapp.adapters.ExerciseModel
import com.example.fitnessapp.databinding.FragmentDaysBinding
import com.example.fitnessapp.utilites.FragmentManager


class DaysFragment : Fragment(), DaysAdapter.Listener {

    private lateinit var binding: FragmentDaysBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclView()
    }

    private fun initRecyclView() = with(binding) {
        val adapter = DaysAdapter(this@DaysFragment) //add Fragment
        rcViewBody.layoutManager = LinearLayoutManager(context as AppCompatActivity)
        rcViewBody.adapter = adapter
        adapter.submitList(fillDaysArray())
    }

    private fun fillDaysArray(): ArrayList<DayModel> {
        val tempArray = ArrayList<DayModel>() // initialisation instance of class
        resources.getStringArray(R.array.day_position).forEach {
            tempArray.add(DayModel(it, false))
        }
        return tempArray
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment() //~ singleton
    }

    override fun onClick(day: DayModel) {
        FragmentManager.setFragment(
            ExercisesListFragment.newInstance(),
            activity as AppCompatActivity
        )
    }


    private fun fillExerciseList(day: DayModel){
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split("_").forEach{
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise  = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(ExerciseModel(exerciseArray[0],exerciseArray[1],exerciseArray[2]))
        }

    }

}