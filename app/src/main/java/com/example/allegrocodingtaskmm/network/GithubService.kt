package com.example.allegrocodingtaskmm.network

import com.example.allegrocodingtaskmm.model.Repo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GithubService {

    @GET("users/allegro/repos")
    suspend fun getRepos(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): List<Repo>
}

object GithubApi  {
    val retrofitService: GithubService by lazy { retrofit.create(GithubService::class.java) }
}