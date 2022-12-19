package com.example.submission_1_fundamental.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission_1_fundamental.ui.detail.FollowFragment

class FollowPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var dataLogin : String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putString(FollowFragment.DATA_LOGIN, dataLogin)
            putInt(FollowFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }
}