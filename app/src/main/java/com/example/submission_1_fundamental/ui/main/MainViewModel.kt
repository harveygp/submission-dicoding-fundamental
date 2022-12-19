package com.example.submission_1_fundamental.ui.main

import androidx.lifecycle.*
import com.example.submission_1_fundamental.domain.repository.UserRepository
import com.example.submission_1_fundamental.data.remote.response.UserItem
import com.example.submission_1_fundamental.utils.Condition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : UserRepository
) : ViewModel()  {

    private var _listUser = MutableLiveData<List<UserItem>?>()
    val listUser: LiveData<List<UserItem>?> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init{
        findUserOnSearch(USER_LOGIN)
    }

    fun findUserOnSearch(login : String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getListSearchUser(login).onEach { condition ->
                when(condition) {
                    is Condition.Loading -> _isLoading.value = true
                    is Condition.Success -> {
                        _isLoading.value = false
                        _listUser.value = condition.data?.items
                    }
                    is Condition.Error -> _isLoading.value = false
                }
            }.launchIn(viewModelScope)
        }
    }


    companion object{
        private const val USER_LOGIN= "user_login"
    }
}