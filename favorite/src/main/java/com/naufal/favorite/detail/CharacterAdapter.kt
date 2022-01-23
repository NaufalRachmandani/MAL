package com.naufal.favorite.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naufal.core.domain.model.anime_characters.CharacterData
import com.naufal.mal.R
import com.naufal.mal.databinding.ItemCharacterBinding
import java.util.*

class CharacterAdapter(
    private val context: Context
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var dataList: List<CharacterData> = Collections.emptyList()

    fun setList(newList: List<CharacterData>) {
        this.dataList = newList
        notifyDataSetChanged()
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(dataList[position])
        }
    }

    override fun getItemCount(): Int = dataList.size
}