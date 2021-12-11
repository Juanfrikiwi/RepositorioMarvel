package com.example.marvelcharacters.data.network

import com.example.marvelcharacters.data.network.models.BaseResponse
import com.example.marvelcharacters.utilities.Constants
import com.example.marvelcharacters.utilities.Constants.Companion.toHex
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MarvelService {

    @GET(Constants.BASE_URL+ Constants.CHARACTERS_URL)
    suspend fun getListCharacters(
        @Query("ts") ts : String = Constants.ts,
        @Query("apikey") apikey : String = Constants.PUBLIC_KEY,
        @Query("hash") hash : String = Constants.md5().toHex(),
        @Query("limit") limit : String = Constants.limit,
        ): BaseResponse

    @GET(Constants.BASE_URL+ Constants.CHARACTERS_URL)
    suspend fun getCharacter(
        @Query("id") id : Int,
        @Query("ts") ts : String = Constants.ts,
        @Query("apikey") apikey : String = Constants.PUBLIC_KEY,
        @Query("hash") hash : String = Constants.md5().toHex(),
        @Query("limit") limit : String = Constants.limit,
    ): BaseResponse


    companion object {
        fun create(): MarvelService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarvelService::class.java)
        }
    }

}
