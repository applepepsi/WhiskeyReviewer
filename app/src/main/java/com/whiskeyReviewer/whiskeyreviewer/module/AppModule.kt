package com.whiskeyReviewer.whiskeyreviewer.module


import com.whiskeyReviewer.oneplusone.serverConnection.API
import com.whiskeyReviewer.whiskeyreviewer.repository.MainRepository
import com.whiskeyReviewer.whiskeyreviewer.repository.MainRepositoryImpl
import com.whiskeyReviewer.whiskeyreviewer.repository.WriteReviewRepository
import com.whiskeyReviewer.whiskeyreviewer.repository.WriteReviewRepositoryImpl

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