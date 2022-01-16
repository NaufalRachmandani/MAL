package com.naufal.mal.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.naufal.mal.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepVisibleCondition {
                homeViewModel.isLoading.value ?: false
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initiateObserver()
        initiateUI()
        initiateData()
    }

    private fun initiateObserver() {

    }

    private fun initiateUI() {
//        val myString = "Mary"
//        val myInt = 12
//        val formatted = getString(R.string.my_xml_string, myString, myInt)
        binding.shimmerViewContainer.startShimmer()
        binding.shimmerViewContainer.visibility = View.VISIBLE
    }

    private fun initiateData() {

    }
}