package com.example.fitnessapp.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.fitnessapp.R
import com.example.fitnessapp.adapters.ExerciseModel
import com.example.fitnessapp.databinding.FragmentExerciseVideoBinding
import com.example.fitnessapp.utilites.FragmentManager
import com.example.fitnessapp.utilites.MainViewModel

const val PACKAGE_NAME = "com.example.fitnessapp"

class ExercisesVideoFragment : Fragment() {

    private lateinit var binding: FragmentExerciseVideoBinding
    private val model: MainViewModel by activityViewModels()
    private var exercisesCounter = 0
    private var exercList: ArrayList<ExerciseModel>? = null
    private var timer: CountDownTimer? = null
    private var actionBarMod : ActionBar? =null
    private var currentDay = 0
    lateinit var videoView: VideoView
    private val videoURL = "http://www.ebookfrenzy.com/android_book/movie.mp4"
    private val videoRAW = "android:resource://$PACKAGE_NAME/${R.raw.dream_lake}"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExerciseVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startVideo(videoURL)

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

    private fun startVideo(URL: String){
        videoView = binding.vvExercHeaderVideo
        val mediaController = MediaController(activity as AppCompatActivity)
        mediaController.show()
        mediaController.setAnchorView(videoView)
        val uri = Uri.parse(URL)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()



    }

    private fun nextExercise() {
        if (exercisesCounter < exercList?.size!!) {
            val exercis = exercList?.get(exercisesCounter++) ?: return
            showExercise(exercis)
            setExerciseType(exercis)
        } else {
            exercisesCounter++
            FragmentManager.setFragment(
                DayFinishFragment.newInstance(),
                activity as AppCompatActivity
            )
        }
    }

    private fun showExercise(exerciseModel: ExerciseModel) = with(binding) {
        startVideo(videoURL)
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
        }
    }


    override fun onDetach() {
        super.onDetach()
        model.savePref(currentDay.toString(), exercisesCounter-1)
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExercisesVideoFragment() //~ singleton
    }

}