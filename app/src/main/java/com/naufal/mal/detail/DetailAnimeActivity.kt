package com.naufal.mal.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.naufal.core.data.source.remote.Resource
import com.naufal.core.domain.model.anime.Anime
import com.naufal.mal.R
import com.naufal.mal.databinding.ActivityDetailAnimeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailAnimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAnimeBinding
    private lateinit var anime: Anime

    private val detailAnimeViewModel by viewModel<DetailAnimeViewModel>()

    private val adapter by lazy {
        CharacterAdapter(
            context = this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        anime = intent.getParcelableExtra(ANIME) ?: Anime()

        detailAnimeViewModel.getCharacters(anime.malId.toString())
        detailAnimeViewModel.isInFavorite(anime)

        initiateObserver()
        initiateUI()
    }

    private fun initiateObserver() {
        detailAnimeViewModel.character.observe(this) {
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

        detailAnimeViewModel.favorite.observe(this) {
            if (it) {
                binding.toolbar.btnAction.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.toolbar.btnAction.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }

    private fun initiateUI() {
        binding.run {
            toolbar.apply {
                toolbarTitle.text = anime.title
                btnBack.visibility = View.VISIBLE
                btnBack.setOnClickListener {
                    onBackPressed()
                }
                btnAction.visibility = View.VISIBLE
                btnAction.setOnClickListener {
                    if (detailAnimeViewModel.favorite.value == true) {
                        detailAnimeViewModel.deleteAnime(anime)
                    } else {
                        detailAnimeViewModel.insertAnime(anime)
                    }
                }
            }

            Glide.with(this@DetailAnimeActivity)
                .load(anime.images?.jpg?.imageUrl)
                .placeholder(R.drawable.mal_logo)
                .error(R.drawable.ic_baseline_error_24)
                .into(ivAnime)
            tvTitle.text = anime.title
            tvScore.text = anime.score.toString()
            tvRank.text = anime.rank.toString()
            tvPopularity.text = anime.popularity.toString()
            tvType.text = getString(R.string.type_eps, anime.type.toString(), anime.episodes.toString())
            tvSeason.text = getString(R.string.season_year, anime.season, anime.year.toString())
            tvStatus.text = anime.status
            tvSource.text = getString(R.string.source_s, anime.source)
            tvRating.text = getString(R.string.rating_s, anime.rating)
            var genres = ""
            val genresSize: Int = anime.genres?.size ?: 0
            if (genresSize == 0) {
                genres = "No genres found"
            } else {
                for (i in 0 until genresSize) {
                    if (i == 0) {
                        genres = anime.genres?.get(i)?.name ?: ""
                    } else {
                        genres += ", ${anime.genres?.get(i)?.name}"
                    }
                }
            }
            tvGenre.text = getString(R.string.genre_s, genres)
            tvSynopsis.text = getString(R.string.synopsis_n_s, anime.synopsis)

            refresh.run {
                setOnRefreshListener {
                    CoroutineScope(Dispatchers.Main).launch {
                        Log.i("DetailAnimeActivity", "refresh: ")
                        detailAnimeViewModel.getCharacters(anime.malId.toString())
                        delay(2000)
                        isRefreshing = false
                    }
                }
            }

            rvCharacter.layoutManager = LinearLayoutManager(this@DetailAnimeActivity)
            rvCharacter.setHasFixedSize(true)
            rvCharacter.adapter = adapter
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
                detailAnimeViewModel.getCharacters(anime.malId.toString())
            }
        }
    }

    private fun showShimmer(show: Boolean) {
        binding.run {
            if (show) {
                rvCharacter.visibility = View.GONE
                shimmerViewContainer.startShimmer()
                shimmerViewContainer.visibility = View.VISIBLE
            } else {
                rvCharacter.visibility = View.VISIBLE
                shimmerViewContainer.stopShimmer()
                shimmerViewContainer.visibility = View.GONE
            }
        }
    }

    companion object {
        const val ANIME = ""
    }
}