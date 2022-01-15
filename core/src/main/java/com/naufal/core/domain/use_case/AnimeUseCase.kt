package com.naufal.core.domain.use_case

import com.naufal.core.data.source.remote.Resource
import com.naufal.core.domain.model.anime.Anime
import com.naufal.core.domain.model.anime_characters.CharacterData
import kotlinx.coroutines.flow.Flow

interface AnimeUseCase {
    fun getAnimeTop(): Flow<Resource<List<Anime>>>
    fun getAnimeCharacter(id: String): Flow<Resource<List<CharacterData>>>
    fun getAnimeSearch(q: String, type: String): Flow<Resource<List<Anime>>>
    fun checkFavorite(id: Int): Flow<Boolean>
    suspend fun insertAnime(anime: Anime)
    suspend fun deleteAnime(anime: Anime)
    fun getAnimeFavorite(): Flow<List<Anime>>
}