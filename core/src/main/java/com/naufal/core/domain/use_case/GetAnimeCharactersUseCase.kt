package com.naufal.core.domain.use_case

import com.naufal.core.data.source.remote.Resource
import com.naufal.core.domain.AnimeRepository
import com.naufal.core.domain.model.anime_characters.CharacterData
import kotlinx.coroutines.flow.Flow

class GetAnimeCharactersUseCase(private val animeRepository: AnimeRepository) {
    suspend operator fun invoke(id: String): Flow<Resource<List<CharacterData>>> =
        animeRepository.getAnimeCharacters(id)
}