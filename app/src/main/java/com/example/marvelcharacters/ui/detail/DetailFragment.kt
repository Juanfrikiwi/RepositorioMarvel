package com.example.marvelcharacters.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.marvelcharacters.R
import com.example.marvelcharacters.databinding.FragmentDetailBinding
import com.example.marvelcharacters.ui.ComicsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.net.Uri
import android.content.Intent
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.utilities.ImageUtils


@AndroidEntryPoint

class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    private lateinit var adapter: ComicsAdapter
    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModels()
    private var characterToAdd: CharacterModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        ).apply {
            viewModel = detailViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        getCharacter()
        initComicAdapter()
        detailViewModel.isFavorite()
        binding.loadingState.apply {
            ivReload.setOnClickListener {
                ivReload.visibility = View.GONE
                getCharacter()
            }
        }
    }

    private fun initComicAdapter() {
        adapter = ComicsAdapter(
            object : ComicsAdapter.ListItemComicClickListener {
                override fun onClickItem(nameComic: String) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.search_to_google)+nameComic)
                    )
                    startActivity(browserIntent)
                }
            }
        )
        binding.comicsList.adapter = adapter
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        binding.loadingState.ivReload.setOnClickListener {
            it.visibility = View.GONE
            getCharacter()
        }

        binding.btnFavorite.setOnClickListener {
            it.visibility = View.GONE
            lifecycleScope.launch {
                detailViewModel.addFavourite(detailViewModel.character)
            }
        }
    }

    private fun initObservers() {
        // Observer that runs when there is a correct response in the getCharacter call
        detailViewModel.successResponse.observe(viewLifecycleOwner) { characters ->
            val characterSelected = characters
            characterSelected?.let {
                characterToAdd = it
            }
            characterSelected.let {
                bindingData(characterToAdd)
                detailViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
                    if (isFavorite) {
                        binding.btnFavorite.visibility = View.GONE
                    } else {
                        binding.btnFavorite.visibility = View.VISIBLE
                    }
                }
            }
        }

        //Observer that runs when there is a failed response on the getCharacter call
        detailViewModel.errorResponse.observe(viewLifecycleOwner) {
            binding.apply {
                loadingState.tvMessageLoading.text =
                    getString(R.string.message_error_connection)
                loadingState.tvMessageLoading.visibility = View.VISIBLE
                loadingState.ivReload.visibility = View.VISIBLE
                loadingState.progressBar.visibility = View.GONE
            }
        }

        detailViewModel.addResponse.observe(viewLifecycleOwner){
            if (it){
                Snackbar.make(
                    binding.root,
                    getString(R.string.character_added),
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }else{
                Snackbar.make(
                    binding.root,
                    getString(R.string.error_ocurred),
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
        }
    }


    private fun getCharacter() {
        binding.apply {
            loadingState.progressBar.visibility = View.VISIBLE
            loadingState.tvMessageLoading.text = getString(R.string.message_loading)
            loadingState.tvMessageLoading.visibility = View.VISIBLE
        }
        lifecycleScope.launch {
            detailViewModel.getCharacter(args.characterId)
        }
    }

    private fun bindingData(itemCharacter: CharacterModel?) {
        binding.apply {
            loadingState.loadingContent.visibility = View.GONE
            groupDetail.visibility = View.VISIBLE
            tvName.text = itemCharacter!!.name
            ImageUtils.loadImage(requireContext(),itemCharacter.thumbnail_path,binding.ivDetailImage)
            tvDescription.text =
                if (itemCharacter.description != "") itemCharacter.description else getString(R.string.character_without_description)
            tvModified.text = getString(R.string.updated_on) + " " + itemCharacter.modified
            (comicsList.adapter as ComicsAdapter).submitList(itemCharacter.comics)
        }
    }
}