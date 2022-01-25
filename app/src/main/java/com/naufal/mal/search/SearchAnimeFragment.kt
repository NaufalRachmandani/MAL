package com.naufal.mal.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.core.data.source.remote.Resource
import com.naufal.mal.databinding.FragmentSearchAnimeBinding
import com.naufal.mal.home.AnimeAdapter
import com.naufal.mal.utils.debounceSearch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchAnimeFragment : Fragment() {

    private var _binding: FragmentSearchAnimeBinding? = null
    private val binding get() = _binding!!

    private val searchAnimeViewModel by viewModel<SearchAnimeViewModel>()

    private val adapter by lazy {
        AnimeAdapter(
            context = requireContext(),
            onClick = {
                val action =
                    SearchAnimeFragmentDirections.actionSearchAnimeFragmentToDetailAnimeFragment(
                        anime = it
                    )
                findNavController().navigate(action)
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateObserver()
        initiateUI()
    }

    private fun initiateObserver() {

    }

    private fun initiateUI() {
        binding.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            etSearch.debounceSearch(
                lifecycle,
                onDebouncingQueryTextChange = {
                    val query = it.trim()
                    if (query.isNotEmpty()) {
                        searchAnimeViewModel.searchAnime(query)
                    } else {
                        searchAnimeViewModel.searchAnime("a")
                    }
                }
            )

            searchAnimeViewModel.searchAnime.observe(viewLifecycleOwner) {
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
                searchAnimeViewModel.searchAnime(etSearch.text?.toString() ?: "")
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}