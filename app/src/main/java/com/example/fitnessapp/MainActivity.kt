package com.example.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnessapp.databinding.ActivityMainBinding
import com.example.fitnessapp.fragments.DaysFragment
import com.example.fitnessapp.utilites.FragmentManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FragmentManager.setFragment(DaysFragment.newInstance(),this)
    }
}