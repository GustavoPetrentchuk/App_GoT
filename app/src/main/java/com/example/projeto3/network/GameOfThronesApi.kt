package com.example.projeto3.network

import com.example.projeto3.data.Character
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://thronesapi.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface GameOfThronesApiService {
    @GET("api/v2/Characters")
    suspend fun getCharacters(): List<Character>
}

object GameOfThronesApi {
    val retrofitService: GameOfThronesApiService by lazy {
        retrofit.create(GameOfThronesApiService::class.java)
    }
}