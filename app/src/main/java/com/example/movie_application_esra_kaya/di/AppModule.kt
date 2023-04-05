package com.example.movie_application_esra_kaya.di

import com.example.movie_application_esra_kaya.domain.repository.MovieRepository
import MovieRepositoryImpl
import com.example.movie_application_esra_kaya.BuildConfig
import com.example.movie_application_esra_kaya.data.remote.services.MovieApi
import com.example.movie_application_esra_kaya.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("api_key", Constants.API_KEY)
                .build()
            chain.proceed(newRequest)
        })
        .addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun providePaprikaApi(): MovieApi {
        return Retrofit.Builder()
            //.baseUrl(Constants.BASE_URL)
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }


}