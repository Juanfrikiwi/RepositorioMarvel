package com.example.marvelcharacters.di

import android.content.Context
import androidx.room.Room
import com.example.marvelcharacters.data.local.database.MarvelDatabase
import com.example.marvelcharacters.data.local.localDataRepository.FavoritesRepositoryImpl
import com.example.marvelcharacters.data.network.MarvelService
import com.example.marvelcharacters.data.network.networkDataRepository.CharactersRepositoryImpl
import com.example.marvelcharacters.domain.usecase.favorites.GetListFavoritesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestAppmodule {
    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, MarvelDatabase::class.java)
            .allowMainThreadQueries()
            .build()


    @Provides
    @Singleton
    @Named("test_repository")
    fun provideFavoritesRepositoryImpl(marvelDatabase: MarvelDatabase): FavoritesRepositoryImpl {
        return FavoritesRepositoryImpl(marvelDatabase.charactersDao())
    }


}