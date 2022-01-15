package com.naufal.core.data.source.remote

import com.naufal.core.data.source.remote.model.anime_characters.AnimeCharactersResponse
import com.naufal.core.data.source.remote.model.anime_list.AnimeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyAnimeListApi {

    @GET("top/anime")
    suspend fun getAnimeTop(@Query("page") page: Int): AnimeListResponse

    @GET("anime/{id}/characters")
    suspend fun getAnimeCharacters(@Path("id") id: String): AnimeCharactersResponse

    @GET("anime")
    suspend fun getAnimeSearch(@Query("q") q: String, @Query("type") type: String): AnimeListResponse
}