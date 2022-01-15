package com.naufal.core.domain.use_case

import com.naufal.core.data.source.local.model.anime.toAnimeEntity
import com.naufal.core.domain.AnimeRepository
import com.naufal.core.domain.model.anime.Anime

class DeleteAnimeUseCase(private val animeRepository: AnimeRepository) {
    suspend operator fun invoke(anime: Anime) {
        animeRepository.deleteAnime(animeEntity = anime.toAnimeEntity())
    }
}