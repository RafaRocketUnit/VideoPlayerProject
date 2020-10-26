package com.globant.videoplayerproject.ui.topGames

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.videoplayerproject.api.RepositoryApi
import com.globant.videoplayerproject.model.AccessToken
import com.globant.videoplayerproject.model.Data
import kotlinx.coroutines.launch

class TopGamesViewModel : ViewModel() {

    private val _accessToken = MutableLiveData<AccessToken>()
    private val _onErrorAccessToken = MutableLiveData<Boolean>()
    private val _listGames = MutableLiveData<List<Data>>()
    private val _onError = MutableLiveData<Boolean>()

    val accessToken: LiveData<AccessToken>
        get() = _accessToken

    val onErrorAccessToken: LiveData<Boolean>
        get() = _onErrorAccessToken

    val listGames: LiveData<List<Data>>
        get() = _listGames

    val onError: LiveData<Boolean>
        get() = _onError

    init {
        getAccessToken()
    }

    private fun getAccessToken() {
        val clientId = "xzpd1f4527fu8fct7p7own0pgi35v5"
        val clientSecret = "171nriwnud80u56cy3m0glkcuj4qew"
        val grantType = "client_credentials"
        viewModelScope.launch {
            val getPropertiesDeferred = RepositoryApi.retrofitServiceToken.getAccessToken(clientId, clientSecret, grantType)
            try {
                val accessToken = getPropertiesDeferred.await()
                _accessToken.value = accessToken
            } catch (e: Exception) {
                _onErrorAccessToken.value = true
            }

        }
    }

    fun getListGames(accessToken: String) {
        viewModelScope.launch {
            val getPropertiesDeferred = RepositoryApi.retrofitService.getTopGamesAsync(accessToken, 100)
            try {
                val topGame = getPropertiesDeferred.await()
                _listGames.value = topGame.data
            } catch (e: Exception) {
                _onError.value = true
            }

        }
    }
}