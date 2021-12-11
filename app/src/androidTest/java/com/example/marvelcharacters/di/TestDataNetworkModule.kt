package com.example.marvelcharacters.di

import com.example.marvelcharacters.data.network.MarvelService
import com.example.marvelcharacters.data.network.networkDataRepository.CharactersRepositoryImpl
import com.example.marvelcharacters.domain.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestDataNetworkModule {
    @Provides
    @Singleton
    @Named("test_service")
    fun provideMarvelService(): MarvelService {
        return MarvelService.create()
    }

    @Provides
    @Singleton
    @Named("test_repository")
    fun provideMarvelRepositoryImpl(marvelService: MarvelService): CharactersRepositoryImpl {
        return CharactersRepositoryImpl(marvelService)
    }


}