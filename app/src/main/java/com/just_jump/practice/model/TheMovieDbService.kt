package com.just_jump.practice.model

import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {
    @GET("popular")
    suspend fun listPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
    ): MovieDbResult
}