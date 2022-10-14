package com.example.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitnessapp.R
import com.example.fitnessapp.adapters.DayModel
import com.example.fitnessapp.databinding.FragmentDaysBinding


class DaysFragment : Fragment() {

    private lateinit var binding: FragmentDaysBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDaysBinding.inflate(inflater,container, false)
        return binding.root
    }

    private fun fillDaysArray() : ArrayList<DayModel>{
        val tempArray = ArrayList<DayModel>() // initialisation instance of class
        resources.getStringArray(R.array.day_position).forEach {
            tempArray.add(DayModel(it,false))
        }
        return tempArray
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment() //~ singleton
    }

}