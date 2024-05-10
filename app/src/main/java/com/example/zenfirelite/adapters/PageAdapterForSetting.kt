package com.example.zenfirelite.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zenfirelite.fragments.Setting_BussinessInformation
import com.example.zenfirelite.fragments.Setting_Users

class PageAdapterForSetting(fm: Fragment): FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Setting_BussinessInformation()
            1 -> Setting_Users()
            else -> Setting_BussinessInformation()
        }
    }
}