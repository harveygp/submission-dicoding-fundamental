package com.example.submission_1_fundamental.ui.favorite

import androidx.lifecycle.*
import com.example.submission_1_fundamental.data.local.entity.UserFavEntity
import com.example.submission_1_fundamental.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel@Inject constructor(
    private val repository : UserRepository
) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFavUser(): LiveData<List<UserFavEntity>> {
        _isLoading.value = false
        return repository.getUserFav()
    }

}