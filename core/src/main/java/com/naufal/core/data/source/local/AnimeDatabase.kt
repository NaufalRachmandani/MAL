package com.naufal.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.naufal.core.data.source.local.model.anime.AnimeEntity

@Database(
    entities = [AnimeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AnimeDatabase : RoomDatabase() {

    abstract val animeDao: AnimeDao

    companion object {
        const val DATABASE_NAME = "anime_db"
    }
}