package com.example.fitnessapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fitnessapp.adapters.ExerciseAdapter
import com.example.fitnessapp.databinding.FragmentExercisesListBinding
import com.example.fitnessapp.databinding.FragmentWaitingBinding
import com.example.fitnessapp.utilites.TimeUtils

const val COUNT_DOWN_TIME = 11000L
class WaitingFragment : Fragment() {

    private lateinit var binding: FragmentWaitingBinding
    private lateinit var adapter: ExerciseAdapter
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWaitingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pBarTimer.max = COUNT_DOWN_TIME.toInt()
        startTimer()
    }

    private fun startTimer() = with(binding) {
        timer = object :CountDownTimer(COUNT_DOWN_TIME, 1){
            override fun onTick(restTime: Long) {
                tvTimer.text = TimeUtils.getTime(restTime)
                pBarTimer.progress = restTime.toInt()
            }

            override fun onFinish() {
                Toast.makeText(activity, "done", Toast.LENGTH_LONG).show()
            }

        }.start()

    }

    override fun onDetach(){
        super.onDetach()
        timer.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = WaitingFragment() //~ singleton
    }
}