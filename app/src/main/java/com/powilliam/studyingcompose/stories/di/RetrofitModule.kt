package com.powilliam.studyingcompose.stories.di

import com.powilliam.studyingcompose.stories.data.HackerNewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideHackerNewsService(): HackerNewsService = Retrofit.Builder()
        .baseUrl("https://hn.algolia.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HackerNewsService::class.java)
}