package com.example.nextclass.module


import com.example.oneplusone.serverConnection.API
import com.example.whiskeyreviewer.repository.MainRepository
import com.example.whiskeyreviewer.repository.MainRepositoryImpl
import com.example.whiskeyreviewer.repository.PagingSource
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
    fun provideMainRepository(api: API): MainRepository {
        return MainRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }


}