package com.naufal.mal.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naufal.core.domain.model.anime.Anime
import com.naufal.mal.R
import com.naufal.mal.databinding.ItemAnimeBinding

class AnimeAdapter(
    private val context: Context,
) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: Anime) {
            binding.apply {
                Glide.with(this.root.context)
                    .load(anime.images?.jpg?.imageUrl)
                    .placeholder(R.drawable.mal_logo)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(ivAnime)

                tvTitle.text = anime.title ?: context.getString(R.string.unknown_anime_title)

                val type = anime.type ?: context.getString(R.string.unknown_type)
                val eps = if (anime.episodes == 0 || anime.episodes == null) {
                    context.getString(R.string.unknown_eps)
                } else {
                    anime.episodes.toString() + " eps"
                }
                tvType.text = context.getString(R.string.type_eps, type, eps)

                val season = anime.season ?: context.getString(R.string.unknown_season)
                val year = if (anime.year == 0 || anime.year == null) {
                    context.getString(R.string.unknown_year)
                } else {
                    anime.year.toString()
                }
                tvSeason.text = context.getString(R.string.season_year, season, year)

                if (anime.rank == 0 || anime.rank == null) {
                    tvRank.text = context.getString(R.string.unknown_rank)
                } else {
                    val temp = anime.rank.toString()
                    tvRank.text = context.getString(R.string.rank, temp)
                }

                val score = if (anime.score == 0.0 || anime.score == null) {
                    context.getString(R.string.unknown_score)
                } else {
                    anime.score.toString()
                }
                tvScore.text = score

                itemView.setOnClickListener {
                    onItemClickListener?.invoke(anime)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeAdapter.ViewHolder {
        return ViewHolder(
            ItemAnimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AnimeAdapter.ViewHolder, position: Int) {
        with(holder) {
            bind(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((Anime) -> Unit)? = null

    fun setOnItemClickListener(listener: (Anime) -> Unit) {
        onItemClickListener = listener
    }
}