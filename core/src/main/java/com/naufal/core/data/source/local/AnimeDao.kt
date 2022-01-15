package com.naufal.core.data.source.local

import androidx.room.*
import com.naufal.core.data.source.local.model.anime.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM animeentity")
    fun getAnimeList(): Flow<List<AnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(animeEntity: AnimeEntity)

    @Delete
    suspend fun deleteAnime(animeEntity: AnimeEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM animeentity WHERE malId = :id)")
    fun exists(id: Int): Flow<Boolean>
}