package com.example.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.fitnessapp.databinding.ActivityMainBinding
import com.example.fitnessapp.fragments.DaysFragment
import com.example.fitnessapp.utilites.FragmentManager
import com.example.fitnessapp.utilites.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.pref = getSharedPreferences("main", MODE_PRIVATE) // save data as key-value, only my app get data
        FragmentManager.setFragment(DaysFragment.newInstance(), this)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (FragmentManager.currentFragment is DaysFragment) {
            super.onBackPressed()
        } else {
            FragmentManager.setFragment(DaysFragment.newInstance(), this)
        }
    }
}