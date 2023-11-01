package com.example.external.provider

import android.util.Log
import com.example.domain.Resource
import com.example.domain.di.DefaultDispatcher
import com.example.domain.model.MovieListPage
import com.example.domain.repository.SearchRepository
import com.example.external.mapper.MovieListPageMapper
import com.example.external.remote.SearchService
import com.example.external.utils.Constants.NETWORK_ERROR_MESSAGE
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class SearchRepositoryProvider @Inject constructor(
    private var searchApi: SearchService,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val movieListPageMapper: MovieListPageMapper
) : SearchRepository {

    override suspend fun getSearchResultList(
        query: String
    ): Resource<MovieListPage> {
        return withContext(defaultDispatcher) {
            try {
                val movieList = searchApi.getSearchResultList(query)
                Resource.Success(movieListPageMapper.map(movieList))
            } catch (e: IOException) {
                Log.d("#PhucNK1 ", e.toString())
                Resource.Error(NETWORK_ERROR_MESSAGE)
            } catch (e: HttpException) {
                Log.d("#PhucNK1 ", e.toString())
                Resource.Error(NETWORK_ERROR_MESSAGE)
            } catch (e: SocketTimeoutException) {
                Log.d("#PhucNK1 ", e.toString())
                Resource.Error(NETWORK_ERROR_MESSAGE)
            }
        }
    }

}