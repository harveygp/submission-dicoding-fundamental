package com.example.submission_1_fundamental.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_1_fundamental.R
import com.example.submission_1_fundamental.adapter.ListUserFavAdapter
import com.example.submission_1_fundamental.common.Constant
import com.example.submission_1_fundamental.data.local.entity.UserFavEntity
import com.example.submission_1_fundamental.databinding.ActivityFavoriteBinding
import com.example.submission_1_fundamental.ui.detail.DetailActivity
import com.example.submission_1_fundamental.ui.setting.SettingHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var recycleUser: RecyclerView
    private val pref by lazy { SettingHelper(this) }

    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite_title)

        checkMode()
        init()
    }

    private fun setListUserData(users: List<UserFavEntity>?){
        val listUsers = users
        val adapter = listUsers?.let { ListUserFavAdapter(it) }
        binding.rvUsers.adapter = adapter
        adapter?.setOnItemClickCallback(object : ListUserFavAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserFavEntity) {
                val intentToDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.DATA_LOGIN, data.login)
                startActivity(intentToDetail)
            }
        })
    }

    private fun checkMode(){
        when(pref.getSwitch(Constant.KEY_PREF)){
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun init(){
        recycleUser = binding.rvUsers
        recycleUser.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recycleUser.layoutManager = layoutManager

        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favoriteViewModel.getFavUser().observe(this){
            setListUserData(it)
        }
    }
}