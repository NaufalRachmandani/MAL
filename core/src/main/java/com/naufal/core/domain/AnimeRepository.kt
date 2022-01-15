package com.naufal.core.domain

import com.naufal.core.data.source.remote.Resource
import com.naufal.core.data.source.local.model.anime.AnimeEntity
import com.naufal.core.domain.model.anime.Anime
import com.naufal.core.domain.model.anime_characters.CharacterData
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getAnimeTop(): Flow<Resource<List<Anime>>>
    fun getAnimeCharacters(id: String): Flow<Resource<List<CharacterData>>>
    fun getAnimeSearch(q: String, type: String): Flow<Resource<List<Anime>>>
    fun getAnimeFavorite(): Flow<List<AnimeEntity>>
    suspend fun insertAnime(animeEntity: AnimeEntity)
    suspend fun deleteAnime(animeEntity: AnimeEntity)
    fun exist(id: Int): Flow<Boolean>
}