package com.naufal.mal.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.core.data.source.remote.Resource
import com.naufal.mal.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()

    private val adapter by lazy {
        AnimeAdapter(
            context = requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateObserver()
        initiateUI()
    }

    private fun initiateObserver() {
        homeViewModel.animeTop.observe(viewLifecycleOwner) {
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
    }

    private fun initiateUI() {
        binding.run {
            toolbar.apply {
                btnAction.visibility = View.VISIBLE
                btnAction.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavoriteGraph())
                }
                btnSearch.visibility = View.VISIBLE
                btnSearch.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchAnimeFragment())
                }
            }

            refresh.run {
                setOnRefreshListener {
                    lifecycle.coroutineScope.launch {
                        Log.i("HomeFragment", "refresh: ")
                        homeViewModel.getAnimeTop()
                        delay(1000)
                        isRefreshing = false
                    }
                }
            }

            adapter.setOnItemClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToDetailAnimeFragment(anime = it)
                findNavController().navigate(action)
            }

            rvAnime.layoutManager = LinearLayoutManager(requireContext())
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

    override fun onPause() {
        super.onPause()
        binding.refresh.isRefreshing = false
    }

    override fun onDestroyView() {
        binding.rvAnime.adapter = null
        super.onDestroyView()
        _binding = null
    }
}