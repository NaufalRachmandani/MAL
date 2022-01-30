package com.naufal.mal.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naufal.core.data.source.remote.Resource
import com.naufal.core.domain.model.anime.Anime
import com.naufal.core.domain.use_case.AnimeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchAnimeViewModel(
    private val animeUseCase: AnimeUseCase
) : ViewModel() {

    private var _searchAnime = MutableLiveData<Resource<List<Anime>>>()
    val searchAnime: LiveData<Resource<List<Anime>>> = _searchAnime

    private var _type = MutableLiveData<String>()
    val type: LiveData<String> = _type

    fun searchAnime(q: String, type: String = AnimeType.ALL.type) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("SearchAnimeViewModel", "searchAnime: $q")
            animeUseCase.getAnimeSearch(q, type)
                .catch {
                    _searchAnime.postValue(Resource.Error(it.message ?: "Error please try again"))
                }.collect {
                    Log.i("SearchAnimeViewModel", "searchAnime: ${it.data?.size}")
                    _searchAnime.postValue(it)
                }
        }
    }
}