package com.example.android.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.databinding.FragmentMovieDetailBinding
import com.example.android.ui.BaseFragment
import com.example.android.viewmodel.MovieDetailViewModel
import com.example.android.viewmodel.SearchViewModel
import javax.inject.Inject

class MovieDetailFragment: BaseFragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private val args: MovieDetailFragmentArgs by navArgs()
    private val movieModel: MovieModel by lazy {
        args.movieModel
    }
    @Inject
    lateinit var viewModel: MovieDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
        initObserveData()
    }

    override fun initObserveData() {
        viewModel.store.observe(
            owner = this,
            selector = { state -> state.isFavorite },
            observer = { isFavorite ->
                if (isFavorite) {
                    viewModel.insert(movieModel.copy(isFavoriteMovie = true))
                } else {
                    viewModel.delete(movieModel.id ?: "")
                }
            }
        )
    }

    override fun initData() {
    }

    override fun initView() {
        val glide = Glide.with(this)
        movieModel.let {
            glide.load(it.artworkUrl100)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error)
                .into(binding.ivMovieImage)

            binding.tvMovieName.text = it.trackCensoredName
            binding.tvMovieDescription.text = it.longDescription

            binding.tvGenres.text = it.genre
            viewModel.isLiked = it.isFavoriteMovie
            val heartIcon = if (it.isFavoriteMovie) R.drawable.heart_active else R.drawable.heart_default
            binding.imgLike.setImageResource(heartIcon)
        }
    }

    override fun initAction() {
        binding.imgLike.setOnClickListener {
            val imageUpdated = if (viewModel.isLiked) {
                //perform update
                viewModel.isLiked = false
                R.drawable.heart_default

            } else {
                //perform update
                viewModel.isLiked = true
                R.drawable.heart_active
            }
            binding.imgLike.setImageResource(imageUpdated)
        }

        binding.ivBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
