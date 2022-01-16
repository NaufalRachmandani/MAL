package com.naufal.mal.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.core.data.source.remote.Resource
import com.naufal.mal.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy {
        AnimeItemAdapter(
            context = this,
            onClick = {

            },
        )
    }

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
    }

    private fun initiateObserver() {
        homeViewModel.animeTop.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showError(false)
                    showShimmer(true)
                }
                is Resource.Error -> {
                    showError(true)
                    showShimmer(false)
                    binding.tvError.text = it.message
                }
                is Resource.Success -> {
                    showError(false)
                    showShimmer(false)
                    adapter.setList(it.data ?: mutableListOf())
                }
            }
        }
    }

    private fun initiateUI() {
        binding.run {
            toolbar.btnAction.visibility = View.VISIBLE
            toolbar.btnAction.setOnClickListener {

            }

            refresh.run {
                setOnRefreshListener {
                    CoroutineScope(Dispatchers.Main).launch {
                        Log.i("MainActivity", "refresh: ")
                        homeViewModel.getAnimeTop()
                        delay(2000)
                        isRefreshing = false
                    }
                }
            }

            rvAnime.layoutManager = LinearLayoutManager(this@MainActivity)
            rvAnime.setHasFixedSize(true)
            rvAnime.adapter = adapter
        }
    }

    private fun showError(show: Boolean) {
        binding.run {
            if (show) {
                ivError.visibility = View.VISIBLE
                tvError.visibility = View.VISIBLE
                btnTryAgain.visibility = View.VISIBLE
            } else {
                ivError.visibility = View.GONE
                tvError.visibility = View.GONE
                btnTryAgain.visibility = View.GONE
            }

            btnTryAgain.setOnClickListener {
                homeViewModel.getAnimeTop()
            }
        }
    }

    private fun showShimmer(show: Boolean) {
        binding.run {
            if (show) {
                binding.shimmerViewContainer.startShimmer()
                binding.shimmerViewContainer.visibility = View.VISIBLE
            } else {
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.GONE
            }
        }
    }
}