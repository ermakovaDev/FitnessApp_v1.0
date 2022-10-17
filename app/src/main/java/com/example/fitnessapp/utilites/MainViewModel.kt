package com.example.fitnessapp.utilites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessapp.adapters.ExerciseModel

class MainViewModel: ViewModel() { //MVVM
     val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()
}