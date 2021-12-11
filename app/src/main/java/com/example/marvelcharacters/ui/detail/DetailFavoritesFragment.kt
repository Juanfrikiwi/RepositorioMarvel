package com.example.marvelcharacters.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.marvelcharacters.R
import com.example.marvelcharacters.databinding.FragmentDetailBinding
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.ui.ComicsAdapter
import com.example.marvelcharacters.utilities.ImageUtils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailFavoritesFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    private val detailFavoritesViewModel: DetailFavoritesViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    lateinit var adapter: ComicsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComicAdapter()
        initListeners()
        initObserver()
        detailFavoritesViewModel.getFavorite(args.characterId)
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }

    fun initObserver(){
        detailFavoritesViewModel.errorResponse.observe(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                getString(R.string.error_ocurred),
                Snackbar.LENGTH_LONG
            )
                .show()
        }

        detailFavoritesViewModel.successResponse.observe(viewLifecycleOwner) { characters ->
            if (characters != null) {
                bindingData(characters)
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.error_ocurred),
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    private fun initComicAdapter() {
        adapter = ComicsAdapter(
            object : ComicsAdapter.ListItemComicClickListener {
                override fun onClickItem(nameComic: String) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.search_to_google) + nameComic)
                    )
                    startActivity(browserIntent)
                }
            }
        )
        binding.comicsList.adapter = adapter
    }

    private fun bindingData(characters: CharacterModel) {
        binding.apply {
            loadingState.loadingContent.visibility = View.GONE
            groupDetail.visibility = View.VISIBLE
            tvName.text = characters.name
            ImageUtils.loadImage(requireContext(), characters.thumbnail_path, binding.ivDetailImage)
            tvDescription.text =
                if (characters.description != "") characters.description else getString(R.string.character_without_description)
            tvModified.text = getString(R.string.updated_on) + " " + characters.modified
            (comicsList.adapter as ComicsAdapter).submitList(characters.comics)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}