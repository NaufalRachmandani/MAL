package com.naufal.core.domain.use_case

import com.naufal.core.data.source.local.model.anime.toAnime
import com.naufal.core.domain.AnimeRepository
import com.naufal.core.domain.model.anime.Anime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetAnimeFavortieUseCase(private val animeRepository: AnimeRepository) {
    suspend operator fun invoke(): Flow<List<Anime>> = flow {
        var animeList: List<Anime> = emptyList()
        animeRepository.getAnimeFavorite().collect { list ->
            animeList = list.map { it.toAnime() }
        }
        emit(animeList)
    }
}