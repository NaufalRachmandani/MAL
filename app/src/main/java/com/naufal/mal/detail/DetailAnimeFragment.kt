package com.naufal.mal.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.naufal.core.data.source.remote.Resource
import com.naufal.core.domain.model.anime.Anime
import com.naufal.mal.R
import com.naufal.mal.databinding.FragmentDetailAnimeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailAnimeFragment : Fragment() {

    private var _binding: FragmentDetailAnimeBinding? = null
    private val binding get() = _binding!!

    private val args: DetailAnimeFragmentArgs by navArgs()

    private val detailAnimeViewModel by viewModel<DetailAnimeViewModel>()
    private lateinit var anime: Anime

    private val adapter by lazy {
        CharacterAdapter(
            context = requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        anime = args.anime
        detailAnimeViewModel.getCharacters(anime.malId.toString())
        detailAnimeViewModel.isInFavorite(anime)

        initiateObserver()
        initiateUI()
    }

    private fun initiateObserver() {
        detailAnimeViewModel.character.observe(viewLifecycleOwner) {
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
                    adapter.differ.submitList(it.data ?: mutableListOf())
                }
            }
        }

        detailAnimeViewModel.favorite.observe(viewLifecycleOwner) {
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
                    findNavController().popBackStack()
                }
                btnAction.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                btnAction.visibility = View.VISIBLE
                btnAction.setOnClickListener {
                    if (detailAnimeViewModel.favorite.value == true) {
                        detailAnimeViewModel.deleteAnime(anime)
                    } else {
                        detailAnimeViewModel.insertAnime(anime)
                    }
                }
            }

            Glide.with(requireContext())
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
                    lifecycle.coroutineScope.launch {
                        Log.i("DetailAnimeFragment", "refresh: ")
                        detailAnimeViewModel.getCharacters(anime.malId.toString())
                        delay(1000)
                        isRefreshing = false
                    }
                }
            }

            rvCharacter.layoutManager = LinearLayoutManager(requireContext())
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

    override fun onDestroyView() {
        binding.rvCharacter.adapter = null
        super.onDestroyView()
        _binding = null
    }
}