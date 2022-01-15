package com.naufal.core.data.source.remote.model.anime_characters


import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime_characters.VoiceActor

data class VoiceActorDto(
    @SerializedName("language")
    val language: String? = "",
    @SerializedName("person")
    val personDto: PersonDto? = PersonDto()
)

fun VoiceActorDto.toVoiceActor(): VoiceActor {
    return VoiceActor(language = language, person = personDto?.toPerson())
}