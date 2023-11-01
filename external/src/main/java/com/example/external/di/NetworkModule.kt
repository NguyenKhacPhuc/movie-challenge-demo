package com.example.external.di

import android.app.Application
import android.content.Context
import com.babylon.certificatetransparency.certificateTransparencyHostnameVerifier
import com.datatheorem.android.trustkit.TrustKit
import com.example.external.local.FavoriteMovieDao
import com.example.external.local.FavoriteMovieDatabase
import com.example.external.remote.SearchService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.net.ssl.HttpsURLConnection
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val Application = "Application"

@Module
@Suppress("UNUSED")
class NetworkModule() {

    private var baseAuthUrl = "https://itunes.apple.com/"

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideCache(application: Application): Cache {
        val cacheSize = 1024 * 1024 * 10L
        return Cache(application.cacheDir, cacheSize)
    }


    @Provides
    @Named(Application)
    fun provideOkHttpClient(cache: Cache): OkHttpClient {

        val url = URL(this.baseAuthUrl)
        val serverHostname = url.host

        // HttpsUrlConnection
        val connection = url.openConnection() as HttpsURLConnection
        url.openConnection()

        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .cache(cache)
            .hostnameVerifier { _, _ -> true }

        return builder.build()
    }


    @Provides
    @Named(Application)
    fun provideRetrofit(@Named(Application) httpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(this.baseAuthUrl)
            .build()
    }


    @Provides
    fun provideSearchService(@Named(Application) retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    fun provideFavoriteService(context: Context): FavoriteMovieDao =
        FavoriteMovieDatabase.getInstance(context).favoriteMovieDao()
}
