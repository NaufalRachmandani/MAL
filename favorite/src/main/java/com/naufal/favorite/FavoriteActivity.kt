package com.naufal.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.favorite.databinding.ActivityFavoriteBinding
import com.naufal.favorite.di.favoriteModule
import com.naufal.mal.detail.DetailAnimeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModel<FavoriteViewModel>()

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

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        initiateObserver()
        initiateUI()
    }

    private fun initiateObserver() {
        favoriteViewModel.favoriteAnimeList.observe(this) {
            adapter.setList(it)
        }
    }

    private fun initiateUI() {
        binding.run {
            toolbar.toolbarTitle.text = getString(R.string.favorite_anime)
            toolbar.btnBack.visibility = View.VISIBLE
            toolbar.btnBack.setOnClickListener {
                onBackPressed()
            }

            rvAnime.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvAnime.setHasFixedSize(true)
            rvAnime.adapter = adapter
        }
    }
}