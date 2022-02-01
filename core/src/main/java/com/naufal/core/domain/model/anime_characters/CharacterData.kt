package com.naufal.core.domain.model.anime_characters

import com.google.gson.annotations.SerializedName

data class CharacterData(
    @SerializedName("character")
    val character: Character? = Character(),
    @SerializedName("role")
    val role: String? = "",
    @SerializedName("voice_actors")
    val voiceActors: List<VoiceActor>? = listOf()
)