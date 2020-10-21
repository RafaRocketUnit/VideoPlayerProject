package com.globant.videoplayerproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.videoplayerproject.api.RepositoryApi
import com.globant.videoplayerproject.model.Data
import kotlinx.coroutines.launch

class TopGamesViewModel : ViewModel() {

    private val _listGames = MutableLiveData<List<Data>>()
    private val _onError = MutableLiveData<Boolean>()

    val listGames: LiveData<List<Data>>
        get() = _listGames

    val onError: LiveData<Boolean>
        get() = _onError

    init {
        getListGames()
    }

    private fun getListGames() {
        viewModelScope.launch {
            val getPropertiesDeferred = RepositoryApi.retrofitService.getTopGamesAsync()
            try {
                val topGame = getPropertiesDeferred.await()
                _listGames.value = topGame.data
            } catch (e: Exception) {
                _onError.value = true
            }

        }
    }
}