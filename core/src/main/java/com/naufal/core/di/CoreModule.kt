package com.naufal.core.di

import androidx.room.Room
import com.naufal.core.common.Constants
import com.naufal.core.data.AnimeRepositoryImpl
import com.naufal.core.data.source.local.AnimeDatabase
import com.naufal.core.data.source.remote.MyAnimeListApi
import com.naufal.core.domain.AnimeRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<AnimeDatabase>().animeDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AnimeDatabase::class.java, AnimeDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(MyAnimeListApi::class.java)
    }
}

val repositoryModule = module {
    single<AnimeRepository> {
        AnimeRepositoryImpl(
            get(),
            get(),
        )
    }
}