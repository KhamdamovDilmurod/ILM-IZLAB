package com.example.ilm_izlab.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.ilm_izlab.model.TrainingCentersModel
import com.example.ilm_izlab.screen.fragments.tablayout.baholar.LevelFragment
import com.example.ilm_izlab.screen.fragments.tablayout.kurslar.CoursesFragment
import com.example.ilm_izlab.screen.fragments.tablayout.yangliklar.NewsFragment

class TabAdapter(fm: FragmentManager, var item: TrainingCentersModel) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return CoursesFragment(item)
            }
            1 -> {
                return NewsFragment()
            }
            2 -> {
                return LevelFragment(item.id)
            }
            else -> {
                return CoursesFragment(item)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "Kurslar"
            }
            1 -> {
                return "Yangliklar"
            }
            2 -> {
                return "Baholar"
            }
        }
        return super.getPageTitle(position)
    }

    override fun getCount(): Int {
        return 3
    }
}