package com.naufal.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naufal.core.domain.model.anime.Anime
import com.naufal.mal.R
import com.naufal.mal.databinding.ItemAnimeBinding
import java.util.*

class AnimeAdapter(
    private val context: Context,
    private val onClick: (Anime) -> Unit,
) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    private var dataList: List<Anime> = Collections.emptyList()

    fun setList(newList: List<Anime>) {
        this.dataList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: Anime) {
            binding.apply {
                Glide.with(context)
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
                    tvRank.text = context.getString(R.string.rank, anime.rank)
                }

                val score = if (anime.score == 0.0 || anime.score == null) {
                    context.getString(R.string.unknown_score)
                } else {
                    anime.score.toString()
                }
                tvScore.text = score

                itemView.setOnClickListener {
                    onClick.invoke(anime)
                }
            }
        }
    }

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
            bind(dataList[position])
        }
    }

    override fun getItemCount(): Int = dataList.size
}