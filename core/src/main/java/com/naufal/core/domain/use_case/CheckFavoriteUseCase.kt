package com.naufal.core.domain.use_case

import com.naufal.core.domain.AnimeRepository
import kotlinx.coroutines.flow.Flow

class CheckFavoriteUseCase(private val animeRepository: AnimeRepository) {
    suspend operator fun invoke(id: Int): Flow<Boolean> = animeRepository.exist(id)
}