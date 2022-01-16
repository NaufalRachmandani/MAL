package com.naufal.mal.home

import android.util.Log
import androidx.lifecycle.*
import com.naufal.core.data.source.remote.Resource
import com.naufal.core.domain.model.anime.Anime
import com.naufal.core.domain.use_case.AnimeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val animeUseCase: AnimeUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _animeTop = MutableLiveData<Resource<List<Anime>>>()
    val animeTop: LiveData<Resource<List<Anime>>> = _animeTop

    init {
        getAnimeTop()
    }

    fun getAnimeTop() {
        Log.i("HomeViewModel", "getAnimeTop: called")
        viewModelScope.launch(Dispatchers.IO) {
            animeUseCase.getAnimeTop()
                .onStart {
                    Log.i("HomeViewModel", "getAnimeTop: loading")
                    _isLoading.postValue(true)
                }.catch {
                    Log.i("HomeViewModel", "getAnimeTop: error ${it.message}")
                    _isLoading.postValue(false)
                    _animeTop.postValue(Resource.Error("${it.message}"))
                }.collect {
                    Log.i("HomeViewModel", "getAnimeTop: collect")
                    _isLoading.postValue(false)
                    _animeTop.postValue(it)
                }
        }
    }
}