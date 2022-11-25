package com.example.hiltwithcompose

import retrofit2.Response
import retrofit2.http.*


interface RetrofitInterface {
    @GET("character")
    suspend fun getCharactersList(
    ): Response<CharactersList>
}