package com.naufal.mal.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.naufal.core.data.source.remote.Resource
import com.naufal.mal.R
import com.naufal.mal.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepVisibleCondition {
                homeViewModel.isLoading.value ?: false
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}