package com.example.fitnessapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.fitnessapp.R
import com.example.fitnessapp.adapters.ExerciseModel
import com.example.fitnessapp.databinding.FragmentExerciseBinding
import com.example.fitnessapp.utilites.FragmentManager
import com.example.fitnessapp.utilites.MainViewModel
import com.example.fitnessapp.utilites.TimeUtils
import pl.droidsonroids.gif.GifDrawable


class ExercisesFragment : Fragment() {

    private lateinit var binding: FragmentExerciseBinding
    private val model: MainViewModel by activityViewModels()
    private var exercisesCounter = 0
    private var exercList: ArrayList<ExerciseModel>? = null
    private var timer: CountDownTimer? = null
    private var actionBarMod : ActionBar? =null
    private var currentDay = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = model.currentDay
        exercisesCounter = model.getExerciseCount()
        actionBarMod = (activity as AppCompatActivity).supportActionBar
        model.mutableListExercise.observe(viewLifecycleOwner) {
            exercList = it
            nextExercise()
        }
        binding.btnExercFooterNext.setOnClickListener {
            nextExercise()
        }
    }

    private fun nextExercise() {
        if (exercisesCounter < exercList?.size!!) {
            val exercis = exercList?.get(exercisesCounter++) ?: return
            showExercise(exercis)
            setExerciseType(exercis)
            showNextExercise()
        } else {
            exercisesCounter++
            FragmentManager.setFragment(
                DayFinishFragment.newInstance(),
                activity as AppCompatActivity
            )
        }
    }

    private fun showExercise(exerciseModel: ExerciseModel) = with(binding) {
        ivExercHeaderImage.setImageDrawable(GifDrawable(root.context.assets, exerciseModel.image))
        tvExercBodyTitle.text = exerciseModel.title
        val textTitleBar = "$exercisesCounter / ${exercList?.size}"
        actionBarMod?.title = textTitleBar
    }

    private fun setExerciseType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            timer?.cancel()
            binding.pbarBodyTimer.visibility = View.INVISIBLE
            binding.tvExercBodyTimer.text = exercise.time
        } else {
            binding.pbarBodyTimer.visibility = View.VISIBLE
            startTimer(exercise)
        }
    }

    private fun showNextExercise() = with(binding) {
        if (exercisesCounter < exercList?.size!!) {
            val exercis = exercList?.get(exercisesCounter) ?: return
            ivExercFooterImage.setImageDrawable(GifDrawable(root.context.assets, exercis.image))
            setTimeType(exercis)
        } else {
            ivExercFooterImage.setImageResource(R.drawable.bg_header)
           // ivExercFooterImage.setImageDrawable(GifDrawable(root.context.assets, "bb_finish.gif"))
            tvExercFooterNextTitle.text = getString(R.string.finish)
        }
    }


    private fun setTimeType(exercise: ExerciseModel) = with(binding) {
        if (exercise.time.startsWith("x")) {
            tvExercFooterNextTitle.text = exercise.time
        } else {
            val title = exercise.title + ": ${TimeUtils.getTime(exercise.time.toLong() * 1000)} "
            tvExercFooterNextTitle.text = title
        }

    }

    private fun startTimer(exercise: ExerciseModel) = with(binding) {
        pbarBodyTimer.max = exercise.time.toInt() * 1000
        timer?.cancel()
        timer = object : CountDownTimer(exercise.time.toLong() * 1000, 1) {

            override fun onTick(restTime: Long) {
                tvExercBodyTimer.text = TimeUtils.getTime(restTime)
                pbarBodyTimer.progress = restTime.toInt()
            }

            override fun onFinish() {
                nextExercise()
            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        model.savePref(currentDay.toString(), exercisesCounter-1)
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExercisesFragment() //~ singleton
    }

}