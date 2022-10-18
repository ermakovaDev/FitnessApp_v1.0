package com.example.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.adapters.ExerciseAdapter
import com.example.fitnessapp.adapters.ExerciseModel
import com.example.fitnessapp.databinding.FragmentExerciseBinding
import com.example.fitnessapp.utilites.MainViewModel
import pl.droidsonroids.gif.GifDrawable


class ExercisesFragment : Fragment() {

    private lateinit var binding: FragmentExerciseBinding
    private val model: MainViewModel by activityViewModels()
    private var exercisesCounter = 0
    private var exercList: ArrayList<ExerciseModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.mutableListExercise.observe(viewLifecycleOwner){
         exercList = it
            nextExercise()
        }
        binding.btnExercFooterNext.setOnClickListener{
            nextExercise()
        }
    }

    private fun nextExercise(){
        if(exercisesCounter < exercList?.size!!){
            val exerc = exercList?.get(exercisesCounter++)
            showExercise(exerc)
        }else{
            Toast.makeText(activity,"DONE", Toast.LENGTH_LONG).show()
        }
    }


    private fun showExercise(exerciseModel: ExerciseModel?) = with(binding){
        if (exerciseModel == null) return@with
        ivExercHeaderImage.setImageDrawable(GifDrawable(root.context.assets, exerciseModel.image))
        tvExercBodyTitle.text = exerciseModel.title
    }
    companion object {
        @JvmStatic
        fun newInstance() = ExercisesFragment() //~ singleton
    }

}