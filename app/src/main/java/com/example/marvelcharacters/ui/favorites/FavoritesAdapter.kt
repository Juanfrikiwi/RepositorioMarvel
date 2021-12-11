package com.example.marvelcharacters.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.databinding.ListItemCharacterBinding
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.ui.viewpager.HomeViewPagerFragmentDirections
import com.example.marvelcharacters.utilities.ImageUtils


class FavoritesAdapter(
    val listItemClickListener: ListItemClickListener
): ListAdapter<CharacterModel, FavoritesAdapter.CharactersViewHolder>(CharactersFavoritesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            ListItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = getItem(position)
        if (character != null) {
            holder.bind(character)
            holder.btnDelete.setOnClickListener {
                listItemClickListener.onDeleteItem(character)
            }
        }
    }

    class CharactersViewHolder(
        private val binding: ListItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val btnDelete = binding.ivDelete
        init {
            binding.ivDelete.visibility = View.VISIBLE
            binding.setClickListener { view ->
                    navigateToDetail(binding.characterModel!!.idCharacter, view)
            }
        }
        private fun navigateToDetail(characterId: Int, view: View) {
            val direction = HomeViewPagerFragmentDirections
                .actionViewPagerFragmentToDetailFavoritesFragment(characterId)
            view.findNavController().navigate(direction)
        }

        fun bind(item: CharacterModel) {
            binding.apply {
                ImageUtils.loadImage(ivPhoto.context,item.thumbnail_path,ivPhoto)
                characterModel = item
                tvName.text = item.name
                executePendingBindings()
            }
        }
    }
    interface ListItemClickListener{
        fun onDeleteItem(charactersEntity: CharacterModel)
    }

}

private class CharactersFavoritesDiffCallback : DiffUtil.ItemCallback<CharacterModel>() {
    override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
        return oldItem.idCharacter == newItem.idCharacter
    }

    override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
        return oldItem == newItem
    }
}
