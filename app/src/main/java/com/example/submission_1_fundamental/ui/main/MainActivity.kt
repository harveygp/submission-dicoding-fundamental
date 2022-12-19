package com.example.submission_1_fundamental.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_1_fundamental.ui.detail.DetailActivity
import com.example.submission_1_fundamental.R
import com.example.submission_1_fundamental.adapter.ListUserAdapter
import com.example.submission_1_fundamental.common.Constant
import com.example.submission_1_fundamental.databinding.ActivityMainBinding
import com.example.submission_1_fundamental.data.remote.response.UserItem
import com.example.submission_1_fundamental.ui.favorite.FavoriteActivity
import com.example.submission_1_fundamental.ui.setting.SettingActivity
import com.example.submission_1_fundamental.ui.setting.SettingHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recycleUser: RecyclerView
    private val pref by lazy { SettingHelper(this) }

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.list_github)

        checkMode()

        recycleUser = binding.rvUsers
        recycleUser.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recycleUser.layoutManager = layoutManager

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.listUser.observe(this) {
            setListUserData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUserOnSearch(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                mainViewModel.findUserOnSearch(newText)
                return false
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setListUserData(users: List<UserItem>?){
        val listUsers = users
        val adapter = listUsers?.let { ListUserAdapter(it) }
        binding.rvUsers.adapter = adapter
        adapter?.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
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
}