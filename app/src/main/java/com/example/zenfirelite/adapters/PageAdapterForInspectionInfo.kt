package com.example.zenfirelite.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zenfirelite.fragments.InspectionInfoDeficiency
import com.example.zenfirelite.fragments.InspectionInfoFormList

@Suppress("DEPRECATION")
class PageAdapterForInspectionInfo(fm: Fragment): FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        Log.d("neel" , position.toString())
        return when(position) {
            0 -> InspectionInfoFormList()
            1 -> InspectionInfoDeficiency()
            else -> InspectionInfoFormList()
        }
    }
}