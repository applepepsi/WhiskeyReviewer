package com.example.nextclass.module

import android.content.Context
import com.example.whiskeyreviewer.BuildConfig

import com.example.whiskeyreviewer.serverConnection.TokenInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)

            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            })
//            .connectTimeout(50, TimeUnit.SECONDS)
//            .readTimeout(50, TimeUnit.SECONDS)
//            .writeTimeout(50, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_ADDRESS)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(
        @ApplicationContext context: Context,

    ): TokenInterceptor {
        return TokenInterceptor(context)
    }

//    @Provides
//    @Singleton
//    fun provideTokenAuthenticator(
//        @ApplicationContext context: Context,
//        tokenRepository: TokenRepository
//    ): TokenAuthenticator {
//        return TokenAuthenticator(context, tokenRepository)
//    }
}