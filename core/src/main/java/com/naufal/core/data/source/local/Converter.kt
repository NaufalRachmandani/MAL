package com.naufal.core.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.naufal.core.data.source.local.model.anime.GenreEntity
import com.naufal.core.data.source.local.model.anime.ImagesEntity
import com.naufal.core.data.source.local.model.anime.JpgEntity

class Converter {

    @TypeConverter
    fun fromImagesEntity(imagesEntity: ImagesEntity): String {
        return Gson().toJson(imagesEntity)
    }

    @TypeConverter
    fun toImagesEntity(json: String): ImagesEntity {
        return Gson().fromJson(json, ImagesEntity::class.java)
    }

    @TypeConverter
    fun fromJpgEntity(jpgEntity: JpgEntity): String {
        return Gson().toJson(jpgEntity)
    }

    @TypeConverter
    fun toJpgEntity(json: String): JpgEntity {
        return Gson().fromJson(json, JpgEntity::class.java)
    }

    @TypeConverter
    fun fromGenreEntityList(list: List<GenreEntity>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toGenreEntityList(json: String): List<GenreEntity> {
        return Gson().fromJson(json, Array<GenreEntity>::class.java).toList()
    }
}