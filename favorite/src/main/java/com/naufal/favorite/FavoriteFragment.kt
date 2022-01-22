package com.naufal.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.favorite.databinding.FragmentFavoriteBinding
import com.naufal.favorite.di.favoriteModule
import com.naufal.mal.databinding.FragmentHomeBinding
import com.naufal.mal.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private val adapter by lazy {
        AnimeAdapter(
            context = requireContext(),
            onClick = {
//                val action = HomeFragmentDirections.actionHomeFragmentToDetailAnimeFragment(anime = it)
//                findNavController().navigate(action)
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateObserver()
        initiateUI()
    }

    private fun initiateObserver() {
        favoriteViewModel.favoriteAnimeList.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
    }

    private fun initiateUI() {
        binding.run {
            toolbar.toolbarTitle.text = getString(R.string.favorite_anime)
            toolbar.btnBack.visibility = View.VISIBLE
            toolbar.btnBack.setOnClickListener {

            }

            rvAnime.layoutManager = LinearLayoutManager(requireContext())
            rvAnime.setHasFixedSize(true)
            rvAnime.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}