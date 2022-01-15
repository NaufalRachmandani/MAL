package com.naufal.core.domain.use_case

import com.naufal.core.data.source.remote.Resource
import com.naufal.core.domain.AnimeRepository
import com.naufal.core.domain.model.anime.Anime
import kotlinx.coroutines.flow.Flow

class GetAnimeSearchUseCase(private val animeRepository: AnimeRepository) {
    suspend operator fun invoke(q: String, type: String): Flow<Resource<List<Anime>>> =
        animeRepository.getAnimeSearch(q, type)
}