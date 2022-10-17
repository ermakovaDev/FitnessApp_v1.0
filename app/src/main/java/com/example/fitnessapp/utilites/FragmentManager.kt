package com.example.fitnessapp.utilites

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitnessapp.R

object FragmentManager {

    var currentFragment: Fragment? = null

    fun setFragment(newFragment: Fragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.clPlaceHolder, newFragment)
        transaction.commit()

        currentFragment = newFragment
    }

}