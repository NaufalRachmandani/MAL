package com.naufal.mal.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naufal.core.data.source.remote.Resource
import com.naufal.core.domain.model.anime.Anime
import com.naufal.core.domain.model.anime_characters.CharacterData
import com.naufal.core.domain.use_case.AnimeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailAnimeViewModel(private val animeUseCase: AnimeUseCase) : ViewModel() {

    private val _character = MutableLiveData<Resource<List<CharacterData>>>()
    val character: LiveData<Resource<List<CharacterData>>> = _character

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> = _favorite

    fun getCharacters(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            animeUseCase.getAnimeCharacter(id).onStart {

            }.catch {
                _character.postValue(Resource.Error(message = it.message ?: "Unknown Error"))
            }.collect {
                _character.postValue(it)
            }
        }
    }

    fun insertAnime(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            if (anime.malId != null && anime.malId != 0) {
                animeUseCase.insertAnime(anime)
            }
        }
    }

    fun deleteAnime(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            animeUseCase.deleteAnime(anime)
        }
    }

    fun isInFavorite(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            animeUseCase.checkFavorite(anime.malId ?: 0).onStart {
            }.catch { e ->
                Log.i("DetailAnimeViewModel", e.toString())
                _favorite.postValue(false)
            }.collect {
                Log.i("DetailAnimeViewModel", "is in favorite ${anime.title} = $it")
                _favorite.postValue(it)
            }
        }
    }
}