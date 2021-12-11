package com.example.marvelcharacters.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.marvelcharacters.R
import com.example.marvelcharacters.databinding.FragmentHomeBinding
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.ui.HomeAdapter
import com.example.marvelcharacters.ui.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class HomeFragment : Fragment() {
    private var adapter = HomeAdapter()
    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
    lateinit var listCharacters: PagingData<CharacterModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.characterList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLoadingAdapter()
        getCharters()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.successResponse.observe(viewLifecycleOwner) {
            val searchViewText = binding.searchCharacter.query.toString()
            if (searchViewText.length > 2) {
                filterListCharacters(it,searchViewText)
            } else {
                lifecycleScope.launch {
                    listCharacters = it
                    adapter.submitData(listCharacters)
                }
            }
        }
        viewModel.errorResponse.observe(viewLifecycleOwner) {

        }
        viewModel.onStart.observe(viewLifecycleOwner) {
            binding.apply {
                characterList.visibility = View.GONE
                loadingState.apply {
                    progressBar.visibility = View.VISIBLE
                    tvMessageLoading.text = getString(R.string.message_loading)
                    tvMessageLoading.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initListeners() {
        // Listener reload button
        binding.loadingState.apply {
            ivReload.setOnClickListener {
                ivReload.visibility = View.GONE
                getCharters()
            }
        }

        // Listener searchView searchCharacters
        binding.searchCharacter.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(searchText: String): Boolean {
                if (searchText.length > 2) {
                    if (::listCharacters.isInitialized) {
                        filterListCharacters(listCharacters,searchText)
                    }
                } else {
                    lifecycleScope.launch {
                        if (::listCharacters.isInitialized) {
                            binding.tvEmptyList.visibility = View.GONE
                            adapter.submitData(listCharacters)
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
        binding.searchCharacter.visibility = View.GONE
    }

    private fun getCharters() {
        lifecycleScope.launch {
            viewModel.getListCharacters()
        }
    }

    private fun setLoadingAdapter() {
        binding.characterList.adapter = adapter.withLoadStateFooter(
            LoadingStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error) {
                binding.loadingState.apply {
                    progressBar.visibility = View.GONE
                    tvMessageLoading.text = getString(R.string.message_error_connection)
                    ivReload.visibility = View.VISIBLE
                }
            } else if (it.refresh is LoadState.NotLoading) {
                binding.apply {
                    searchCharacter.visibility = View.VISIBLE
                    characterList.visibility = View.VISIBLE
                    loadingState.apply {
                        progressBar.visibility = View.GONE
                        tvMessageLoading.visibility = View.GONE
                    }
                }
            }
        }
    }

    fun filterListCharacters(favorites: PagingData<CharacterModel>, filterText: String) {
        val listFilter = favorites.filter {
            it.name.lowercase().contains(filterText.lowercase())
        }
        lifecycleScope.launch {
            adapter.submitData(listFilter)
        }
        adapter.notifyDataSetChanged()
        if (adapter.itemCount == 0) {
            binding.tvEmptyList.apply {
                visibility = View.VISIBLE
                text = context.getString(R.string.without_result)
            }
        } else {
            binding.tvEmptyList.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


}