package com.example.nextclass.module


import com.example.oneplusone.serverConnection.API
import com.example.whiskeyreviewer.repository.WriteReviewRepository
import com.example.whiskeyreviewer.repository.WriteReviewRepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideWriteReviewRepository(api: API): WriteReviewRepository {
        return WriteReviewRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }

}