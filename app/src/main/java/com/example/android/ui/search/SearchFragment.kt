package com.example.android.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.adapter.SearchResultListAdapter
import com.example.android.databinding.FragmentSearchBinding
import com.example.android.ui.BaseFragment
import com.example.android.viewmodel.SearchViewModel
import com.example.domain.model.Movie
import com.example.domain.model.MovieListPage
import com.example.external.shared.PreferenceStorage
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchListAdapter: SearchResultListAdapter by lazy {
        SearchResultListAdapter { movie: MovieModel -> goToMovieDetailPage(movie) }
    }
    @Inject
    lateinit var preferenceStorage: PreferenceStorage

    @Inject
    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObserveData()
        initData()
        initAction()
        storedVisitTime()
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("SimpleDateFormat")
    private fun storedVisitTime() {
        val sdf = SimpleDateFormat("'Date: 'dd-MM-yyyy' and Time: 'HH:mm:ss z")
        preferenceStorage.storeVisitTime(sdf.format(Date()))
    }

    override fun initObserveData() {

        searchViewModel.store.observe(
            owner = this,
            selector = { state -> state.result },
            observer = { result ->
                updateMovieList(result)
            }
        )

        searchViewModel.store.observeDistinctValue(
            owner = this,
            selector = { state -> state.isLoading },
            observer = { isLoading ->
                showLoading(isLoading)
            }
        )
    }

    override fun initData() {
        //GET init data
    }

    override fun initView() {
        preferenceStorage.getStoredVisitTime().let {
            binding.edtSearch.hint = it.ifEmpty {
                "Search"
            }
        }
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvSearchResultList.layoutManager = LinearLayoutManager(context)
        binding.rvSearchResultList.adapter = searchListAdapter
    }

    override fun initAction() {
        binding.ivSearch.setOnClickListener {
            searchMovie()
        }
        binding.tvCancel.setOnClickListener {
            hideKeyboard()
            clearEditText()
            searchViewModel.clearResult()
        }
    }

    private fun searchMovie() {
        hideKeyboard()
        //Perform Search
        searchViewModel.clearResult()
        searchViewModel.search(binding.edtSearch.text.toString())
    }


    private fun updateMovieList(movieListPagers: List<Movie>) {
        searchListAdapter.submitList(movieListPagers)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading()
        } else {
            dismiss()
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE)
        (imm as? InputMethodManager)?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun clearEditText() {
        binding.edtSearch.text.clear()
    }

    private fun goToMovieDetailPage(movie: MovieModel) {
        val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(movie)
        findNavController().navigate(action)
    }
}