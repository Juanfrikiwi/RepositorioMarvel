package com.example.marvelcharacters.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.marvelcharacters.R
import com.example.marvelcharacters.databinding.FragmentHomeBinding
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.ui.FavoritesAdapter
import com.example.marvelcharacters.ui.dialogs.GenericDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: FavoritesAdapter
    lateinit var listCharacters: List<CharacterModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FavoritesAdapter(
            object : FavoritesAdapter.ListItemClickListener {
                override fun onDeleteItem(characterModel: CharacterModel) {
                    val dialog = GenericDialog
                    dialog.open(
                        onAccept = {
                            lifecycleScope.launch {
                                viewModel.deleteCharacter(characterModel)
                            }
                        }
                    ).show(childFragmentManager, tag)
                }
            }
        )

        binding.characterList.adapter = adapter
        initListeners()
        initObservers()
        viewModel.getListFavorites()
    }

    private fun initObservers() {
        viewModel.successResponse.observe(viewLifecycleOwner) { characters ->
            listCharacters = characters
            if (characters.isNotEmpty()) {
                val searchViewText = binding.searchCharacter.query.toString()
                if (searchViewText.length > 2) {
                    filterListFavorites(listCharacters,searchViewText)
                } else {
                    binding.tvEmptyList.visibility = View.GONE
                    binding.searchCharacter.visibility = View.VISIBLE
                    adapter.submitList(characters)
                }
                binding.searchCharacter.visibility = View.VISIBLE
            } else {
                adapter.submitList(emptyList())
                binding.tvEmptyList.visibility = View.VISIBLE
            }
        }
        viewModel.errorResponse.observe(viewLifecycleOwner) {
            adapter.submitList(emptyList())
            binding.tvEmptyList.visibility = View.VISIBLE
        }

        viewModel.deleteResponse.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                viewModel.getListFavorites()
                binding.tvEmptyList.visibility = View.GONE
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.error_ocurred),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    fun filterListFavorites(favorites:List<CharacterModel>,filterText:String){
        val listFilter = favorites.filter {
            it.name.lowercase().contains(filterText.lowercase())
        }
        adapter.submitList(listFilter)
        if (listFilter.isEmpty()) {
            binding.tvEmptyList.apply {
                visibility = View.VISIBLE
                text = context.getString(R.string.without_result)
            }
        } else {
            binding.tvEmptyList.visibility = View.GONE
        }
    }

    private fun initListeners() {

        // Listener searchView searchCharacters
        binding.searchCharacter.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(searchText: String): Boolean {
                if (searchText.length > 2) {
                    if (::listCharacters.isInitialized) {
                        filterListFavorites(listCharacters, searchText)
                    }
                } else {
                    if (::listCharacters.isInitialized) {
                        if (listCharacters.isEmpty()) {
                            binding.tvEmptyList.apply {
                                visibility = View.VISIBLE
                                text = context.getString(R.string.empty_list)
                            }
                        } else {
                            adapter.submitList(listCharacters)
                            binding.tvEmptyList.visibility = View.GONE
                        }
                    }
                }
                adapter.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })
    }

}