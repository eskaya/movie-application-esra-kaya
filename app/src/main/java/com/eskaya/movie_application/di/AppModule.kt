package com.eskaya.movie_application.di

import com.eskaya.movie_application.domain.repository.MovieRepository
import MovieRepositoryImpl
import com.eskaya.movie_application.data.remote.services.MovieApi
import com.eskaya.movie_application.utils.Constants
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
            .baseUrl(Constants.BASE_URL)
           // .baseUrl(BuildConfig.BASE_URL)
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