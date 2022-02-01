package com.naufal.favorite.di

import com.naufal.favorite.detail.DetailAnimeViewModel
import com.naufal.favorite.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailAnimeViewModel(get()) }
}