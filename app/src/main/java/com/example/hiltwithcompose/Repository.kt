package com.example.hiltwithcompose

import retrofit2.Response
import javax.inject.Inject


class UserRepository @Inject constructor(private val retrofitInterface: RetrofitInterface) {

    suspend fun users(): Response<CharactersList> {
        return retrofitInterface.getCharactersList()
    }
}