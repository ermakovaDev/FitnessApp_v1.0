package com.example.fitnessapp.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost // new menu
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.R
import com.example.fitnessapp.adapters.DayModel
import com.example.fitnessapp.adapters.DaysAdapter
import com.example.fitnessapp.adapters.ExerciseModel
import com.example.fitnessapp.databinding.FragmentDaysBinding
import com.example.fitnessapp.utilites.FragmentManager
import com.example.fitnessapp.utilites.MainViewModel

class DaysFragment : Fragment(), DaysAdapter.Listener {

    private lateinit var binding: FragmentDaysBinding
    private val model: MainViewModel by activityViewModels()
    private var actionBarMod: ActionBar? = null
    private lateinit var adapter: DaysAdapter

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

        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            @SuppressLint("CommitPrefEdits")
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.reset) {
                    model.pref?.edit()?.clear()?.apply()
                    adapter.submitList(fillDaysArray())
                }
                return true
            }

        }, viewLifecycleOwner)
        model.currentDay = 0
        initRecyclView()
    }

    /*
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }
*/

    private fun initRecyclView() = with(binding) {
        adapter = DaysAdapter(this@DaysFragment) //add Fragment
        actionBarMod = (activity as AppCompatActivity).supportActionBar
        actionBarMod?.title = getString(R.string.day_list)
        rcViewBody.layoutManager = LinearLayoutManager(context as AppCompatActivity)
        rcViewBody.adapter = adapter
        adapter.submitList(fillDaysArray())
    }

    private fun fillExerciseList(day: DayModel) {
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split("_").forEach {
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(ExerciseModel(exerciseArray[0], exerciseArray[1], exerciseArray[2], false))
        }
        model.mutableListExercise.value = tempList
    }


    private fun fillDaysArray(): ArrayList<DayModel> {
        val tempArray = ArrayList<DayModel>() // initialisation instance of class
        var daysDoneCounter = 0
        resources.getStringArray(R.array.day_position).forEach {
            model.currentDay++
            val exerciseCounter = it.split("_").size
            tempArray.add(DayModel(it, 0, model.getExerciseCount() == exerciseCounter))
        }
        binding.progressBarHeader.max = tempArray.size
        tempArray.forEach {
            if (it.isDone) {
                daysDoneCounter++
            }
        }
        updateRestDaysUI(tempArray.size - daysDoneCounter, tempArray.size)
        return tempArray
    }

    private fun updateRestDaysUI(restDays: Int, days: Int) = with(binding) {
        val rDays = getString(R.string.rest_days) + " $restDays"
        tvProgressTextHeader.text = rDays
        progressBarHeader.progress = days - restDays
    }

    override fun onClick(day: DayModel) {
        fillExerciseList(day)
        model.currentDay = day.dayNumber
        FragmentManager.setFragment(
            ExercisesListFragment.newInstance(),
            activity as AppCompatActivity
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment() //~ singleton
    }

}