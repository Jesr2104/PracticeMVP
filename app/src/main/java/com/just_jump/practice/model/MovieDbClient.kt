package com.just_jump.practice.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieDbClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/movie/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: TheMovieDbService = retrofit.create(TheMovieDbService::class.java)
}