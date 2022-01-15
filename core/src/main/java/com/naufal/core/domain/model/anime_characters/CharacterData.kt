package com.naufal.core.domain.model.anime_characters

data class CharacterData(
    val character: Character? = Character(),
    val role: String? = "",
    val voiceActors: List<VoiceActor>? = listOf()
)