package com.naufal.mal

import android.app.Application
import com.naufal.core.di.databaseModule
import com.naufal.core.di.networkModule
import com.naufal.core.di.repositoryModule
import com.naufal.mal.di.useCaseModule
import com.naufal.mal.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}