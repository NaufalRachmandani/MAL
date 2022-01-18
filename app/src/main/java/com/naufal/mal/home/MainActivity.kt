package com.naufal.mal.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.core.data.source.remote.Resource
import com.naufal.mal.databinding.ActivityMainBinding
import com.naufal.mal.detail.DetailAnimeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy {
        AnimeAdapter(
            context = this,
            onClick = {
                val intent = Intent(this, DetailAnimeActivity::class.java)
                intent.putExtra(DetailAnimeActivity.ANIME, it)
                startActivity(intent)
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
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
                val uri = Uri.parse("mal://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
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
                rvAnime.visibility = View.GONE
                shimmerViewContainer.startShimmer()
                shimmerViewContainer.visibility = View.VISIBLE
            } else {
                rvAnime.visibility = View.VISIBLE
                shimmerViewContainer.stopShimmer()
                shimmerViewContainer.visibility = View.GONE
            }
        }
    }
}