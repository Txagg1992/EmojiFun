package com.curiousapps.apimyapi.di

import com.curiousapps.apimyapi.data.EmojiListRepositoryImpl
import com.curiousapps.apimyapi.domain.EmojiListRepository
import com.curiousapps.apimyapi.network.EmojiApi
import com.curiousapps.apimyapi.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmojiModule {

    @Provides
    @Singleton
    fun provideEmojiApi(): EmojiApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmojiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEmojiRepository(
        api: EmojiApi
    ): EmojiListRepository = EmojiListRepositoryImpl(api = api)
}