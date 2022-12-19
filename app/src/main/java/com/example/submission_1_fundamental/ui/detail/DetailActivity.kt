package com.example.submission_1_fundamental.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission_1_fundamental.R
import com.example.submission_1_fundamental.adapter.FollowPagerAdapter
import com.example.submission_1_fundamental.common.Constant
import com.example.submission_1_fundamental.data.remote.response.UserDetail
import com.example.submission_1_fundamental.databinding.ActivityDetailBinding
import com.example.submission_1_fundamental.ui.setting.SettingHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val pref by lazy { SettingHelper(this) }

    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        checkMode()

        val name = intent.getStringExtra(DATA_LOGIN) ?: ""

        init(name)
    }

    private fun init(name : String) {

        val followPagerAdapter = FollowPagerAdapter(this)
        followPagerAdapter.dataLogin = name
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter = followPagerAdapter
        val followTabs : TabLayout = binding.tabs
        TabLayoutMediator(followTabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { detailViewModel.setPage(it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("Tab pindah", tab?.position.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("Tab Terpakai", tab?.position.toString())
            }
        })

        detailViewModel.userDetail(name)

        detailViewModel.userDetail.observe(this) {
            binding.tvItemUsername.text = getString(R.string.user_username,detailViewModel.userDetail.value?.login.toString() )
            binding.tvItemName.text = detailViewModel.userDetail.value?.name.toString()
            binding.tvItemCompany.text = detailViewModel.userDetail.value?.company.toString()
            binding.tvItemFollowing.text = detailViewModel.userDetail.value?.following.toString()
            binding.tvItemFollower.text = detailViewModel.userDetail.value?.followers.toString()
            binding.tvItemLocation.text = detailViewModel.userDetail.value?.location.toString()
            Glide.with(this@DetailActivity)
                .load(detailViewModel.userDetail.value?.avatarUrl)
                .circleCrop()
                .into(binding.imgItemPhoto)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.setUserFollower(name)

        binding.fab.setOnClickListener {
            detailViewModel.setUserFavorite(detailViewModel.userDetail)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun checkMode(){
        when(pref.getSwitch(Constant.KEY_PREF)){
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

        const val DATA_LOGIN = "data_login"
    }
}