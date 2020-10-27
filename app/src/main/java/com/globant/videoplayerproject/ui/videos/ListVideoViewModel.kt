package com.globant.videoplayerproject.ui.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.videoplayerproject.api.RepositoryApi
import com.globant.videoplayerproject.model.DataVideo
import kotlinx.coroutines.launch

class ListVideoViewModel : ViewModel() {

    private val _listVideos = MutableLiveData<List<DataVideo>>()
    private val _onError = MutableLiveData<Boolean>()

    val listVideos: LiveData<List<DataVideo>>
        get() = _listVideos

    val onError: LiveData<Boolean>
        get() = _onError

    fun getListVideos(accessToken: String, user_id: String) {
        viewModelScope.launch {
            val getPropertiesDeferred =
                RepositoryApi.retrofitService.getVideosAsync(accessToken, user_id)
            try {
                val topGame = getPropertiesDeferred.await()
                _listVideos.value = topGame.data
            } catch (e: Exception) {
                _onError.value = true
            }
        }
    }
}