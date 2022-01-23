package com.naufal.favorite.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naufal.core.domain.model.anime.Anime
import com.naufal.core.domain.use_case.AnimeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FavoriteViewModel(private val animeUseCase: AnimeUseCase) : ViewModel() {

    private val _favoriteAnimeList = MutableLiveData<List<Anime>>()
    val favoriteAnimeList: LiveData<List<Anime>> = _favoriteAnimeList

    init {
        getFavoriteAnimeList()
    }

    private fun getFavoriteAnimeList() {
        viewModelScope.launch(Dispatchers.IO) {
            animeUseCase.getAnimeFavorite()
                .onStart {
                    Log.i("FavoriteViewModel", "getFavoriteAnimeList: onStart")
                }.catch {
                    Log.i("FavoriteViewModel", "getFavoriteAnimeList: error")
                }.collect {
                    _favoriteAnimeList.postValue(it)
                    Log.i("FavoriteViewModel", "getFavoriteAnimeList: collect ${it.size}")
                }
        }
    }
}