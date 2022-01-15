package com.naufal.core.data

import com.naufal.core.data.source.remote.Resource
import com.naufal.core.data.source.local.AnimeDao
import com.naufal.core.data.source.local.model.anime.AnimeEntity
import com.naufal.core.data.source.remote.MyAnimeListApi
import com.naufal.core.data.source.remote.model.anime_characters.toCharacterData
import com.naufal.core.data.source.remote.model.anime_list.toAnime
import com.naufal.core.domain.AnimeRepository
import com.naufal.core.domain.model.anime.Anime
import com.naufal.core.domain.model.anime_characters.CharacterData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AnimeRepositoryImpl (private val myAnimeListApi: MyAnimeListApi, private val animeDao: AnimeDao) :
    AnimeRepository {

    override fun getAnimeTop(): Flow<Resource<List<Anime>>> = flow {
        try {
            emit(Resource.Loading())
            val response = myAnimeListApi.getAnimeTop(1).data?.map { it.toAnime() } ?: mutableListOf()
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        }
    }

    override fun getAnimeCharacters(id: String): Flow<Resource<List<CharacterData>>> =
        flow {
            try {
                emit(Resource.Loading())
                val characterDataList: List<CharacterData> =
                    myAnimeListApi.getAnimeCharacters(id).data?.map { it.toCharacterData() }
                        ?: mutableListOf()
                emit(Resource.Success(data = characterDataList))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: IOException) {
                emit(Resource.Error("Check your internet connection"))
            }
        }

    override fun getAnimeSearch(q: String, type: String): Flow<Resource<List<Anime>>> =
        flow {
            try {
                emit(Resource.Loading())
                val animeList: List<Anime> =
                    myAnimeListApi.getAnimeSearch(q, type).data?.map { it.toAnime() }
                        ?: mutableListOf()
                emit(Resource.Success(data = animeList))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: IOException) {
                emit(Resource.Error("Check your internet connection"))
            }
        }

    override fun getAnimeFavorite(): Flow<List<AnimeEntity>> {
        return animeDao.getAnimeList()
    }

    override suspend fun insertAnime(animeEntity: AnimeEntity) {
        animeDao.insertAnime(animeEntity)
    }

    override suspend fun deleteAnime(animeEntity: AnimeEntity) {
        animeDao.deleteAnime(animeEntity)
    }

    override fun exist(id: Int): Flow<Boolean> = animeDao.exists(id)
}