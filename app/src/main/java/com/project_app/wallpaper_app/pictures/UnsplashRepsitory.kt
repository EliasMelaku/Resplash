package com.project_app.wallpaper_app.pictures

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.project_app.wallpaper_app.api.UnsplashAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepsitory @Inject constructor(private val unsplashApi: UnsplashAPI) {
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 80,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UnsplashPagingSource(unsplashApi, query)
            }
        ).liveData

}