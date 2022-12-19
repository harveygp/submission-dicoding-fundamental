package com.example.submission_1_fundamental.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission_1_fundamental.domain.repository.UserRepository
import com.example.submission_1_fundamental.data.remote.response.UserDetail
import com.example.submission_1_fundamental.data.remote.response.UserFollow
import com.example.submission_1_fundamental.utils.Condition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository : UserRepository
): ViewModel() {

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    private val _pageNumber = MutableLiveData<Int>()
    val pageNumber: LiveData<Int> = _pageNumber

    private val _userFollow = MutableLiveData<List<UserFollow>>()
    val userFollow: LiveData<List<UserFollow>> = _userFollow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        userDetail(DATA_LOGIN)
        setUserFollower(DATA_LOGIN)
        setUserFollowing(DATA_LOGIN)
    }

    fun userDetail(login : String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUser(login).onEach { condition ->
                when(condition){
                    is Condition.Loading -> _isLoading.value = true
                    is Condition.Success ->{
                        _isLoading.value = false
                        _userDetail.value = condition.data!!
                    }
                    is Condition.Error -> _isLoading.value = false
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setUserFollower(login : String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserFollowers(login).onEach { condition ->
                when(condition){
                    is Condition.Loading -> _isLoading.value = true
                    is Condition.Success -> {
                        _isLoading.value = false
                        _userFollow.value = condition.data!!
                    }
                    is Condition.Error -> _isLoading.value = false
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setUserFollowing(login : String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserFollowing(login).onEach { condition ->
                when(condition){
                    is Condition.Loading -> _isLoading.value = true
                    is Condition.Success -> {
                        _isLoading.value = false
                        _userFollow.value = condition.data!!
                    }
                    is Condition.Error -> _isLoading.value = false
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setPage(index : Int){
        _pageNumber.value = index
    }

    fun setUserFavorite(user : LiveData<UserDetail>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.setUserFavorite(user)
        }
    }

    companion object{
        private const val DATA_LOGIN= "user_login"
    }
}