package com.project_app.wallpaper_app.api

//import androidx.viewbinding.BuildConfig
import com.project_app.wallpaper_app.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashAPI {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val API_KEY = BuildConfig.UNSPLASH_ACCESS_KEY
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $API_KEY")

    @GET("search/photos")

    suspend fun searchPhoto(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse
}