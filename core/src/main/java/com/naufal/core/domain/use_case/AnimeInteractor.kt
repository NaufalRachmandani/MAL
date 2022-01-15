package com.naufal.core.domain.use_case

import com.naufal.core.data.source.local.model.anime.toAnime
import com.naufal.core.data.source.local.model.anime.toAnimeEntity
import com.naufal.core.data.source.remote.Resource
import com.naufal.core.domain.AnimeRepository
import com.naufal.core.domain.model.anime.Anime
import com.naufal.core.domain.model.anime_characters.CharacterData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class AnimeInteractor(private val animeRepository: AnimeRepository) : AnimeUseCase {
    override fun getAnimeTop(): Flow<Resource<List<Anime>>> = animeRepository.getAnimeTop()

    override fun getAnimeCharacter(id: String): Flow<Resource<List<CharacterData>>> =
        animeRepository.getAnimeCharacters(id)

    override fun getAnimeSearch(q: String, type: String): Flow<Resource<List<Anime>>> =
        animeRepository.getAnimeSearch(q, type)

    override fun checkFavorite(id: Int): Flow<Boolean> = animeRepository.exist(id)

    override suspend fun insertAnime(anime: Anime) =
        animeRepository.insertAnime(animeEntity = anime.toAnimeEntity())

    override suspend fun deleteAnime(anime: Anime) =
        animeRepository.deleteAnime(animeEntity = anime.toAnimeEntity())

    override fun getAnimeFavorite(): Flow<List<Anime>> = flow {
        var animeList: List<Anime> = emptyList()
        animeRepository.getAnimeFavorite().collect { list ->
            animeList = list.map { it.toAnime() }
        }
        emit(animeList)
    }
}