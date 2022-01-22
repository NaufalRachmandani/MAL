package com.naufal.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naufal.favorite.databinding.ActivityFavoriteBinding
import com.naufal.favorite.di.favoriteModule
import org.koin.core.context.GlobalContext.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)
    }
}