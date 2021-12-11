package com.example.marvelcharacters.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.databinding.ListItemComicsBinding


class ComicsAdapter(
    val listItemComicClickListener: ListItemComicClickListener
): ListAdapter <String, ComicsAdapter.ComicsViewHolder>(ComicsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        return ComicsViewHolder(
            ListItemComicsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        val comic = getItem(position)
        if (comic != null) {
            holder.bind(comic)
            holder.itemView.setOnClickListener{
                listItemComicClickListener.onClickItem(comic)
            }
        }
    }

    class ComicsViewHolder(
        private val binding: ListItemComicsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.apply {
                binding.tvNameComic.text = item
            }
        }
    }
    interface ListItemComicClickListener{
        fun onClickItem(nameComic: String)
    }

}

private class ComicsDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
