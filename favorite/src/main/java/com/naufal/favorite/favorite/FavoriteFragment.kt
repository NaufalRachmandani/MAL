package com.naufal.favorite.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.favorite.R
import com.naufal.favorite.databinding.FragmentFavoriteBinding
import com.naufal.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private val adapter by lazy {
        AnimeAdapter(
            context = requireContext()
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

        GlobalContext.loadKoinModules(favoriteModule)

        initiateObserver()
        initiateUI()
    }

    private fun initiateObserver() {
        favoriteViewModel.favoriteAnimeList.observe(viewLifecycleOwner) {
            adapter.differ.submitList(it)
        }
    }

    private fun initiateUI() {
        binding.run {
            toolbar.apply {
                toolbarTitle.text = getString(R.string.favorite_anime)
                btnBack.visibility = View.VISIBLE
                btnBack.setOnClickListener {
                    findNavController().popBackStack()
                }
            }

            adapter.setOnItemClickListener {
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailAnimeFragment(it)
                findNavController().navigate(action)
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