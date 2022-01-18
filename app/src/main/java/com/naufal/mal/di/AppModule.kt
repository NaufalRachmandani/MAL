package com.naufal.mal.di

import com.naufal.core.domain.use_case.AnimeInteractor
import com.naufal.core.domain.use_case.AnimeUseCase
import com.naufal.mal.detail.DetailAnimeViewModel
import com.naufal.mal.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<AnimeUseCase> { AnimeInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailAnimeViewModel(get()) }
}