package com.naufal.favorite.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naufal.core.domain.model.anime_characters.CharacterData
import com.naufal.mal.R
import com.naufal.mal.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val context: Context
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characterData: CharacterData) {
            binding.apply {
                Glide.with(context)
                    .load(characterData.character?.images?.jpg?.imageUrl)
                    .placeholder(R.drawable.mal_logo)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(ivCharacter)
                tvCharacterName.text = characterData.character?.name
                tvRole.text = characterData.role

                val voiceActor =
                    characterData.voiceActors?.find { it.language == "Japanese" }?.person
                Glide.with(context)
                    .load(voiceActor?.images?.jpg?.imageUrl)
                    .placeholder(R.drawable.mal_logo)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(ivVoiceActor)
                tvVoiceActorName.text = voiceActor?.name
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<CharacterData>() {
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.character?.name == newItem.character?.name
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.ViewHolder {
        return ViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterAdapter.ViewHolder, position: Int) {
        with(holder) {
            bind(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}