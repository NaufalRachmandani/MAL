package com.naufal.core.data.source.remote.model.anime_characters


import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime_characters.CharacterData

data class CharacterDataDto(
    @SerializedName("character")
    val characterDto: CharacterDto? = CharacterDto(),
    @SerializedName("role")
    val role: String? = "",
    @SerializedName("voice_actors")
    val voiceActorsDto: List<VoiceActorDto>? = listOf()
)

fun CharacterDataDto.toCharacterData(): CharacterData {
    return CharacterData(
        character = characterDto?.toCharacter(),
        role = role,
        voiceActors = voiceActorsDto?.map { it.toVoiceActor() }
    )
}